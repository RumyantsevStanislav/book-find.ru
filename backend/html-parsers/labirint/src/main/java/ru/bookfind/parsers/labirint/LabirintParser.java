package ru.bookfind.parsers.labirint;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.bookfind.parsers.labirint.dtos.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class LabirintParser {
    private final Document document;

    public LabirintParser(Connection connection) throws IOException {
        this.document = connection.execute().parse();
    }

    public Book getBook() {
        Book book = new Book();
        book.setLabirintId(getLabirintId(document));
        book.setTitle(getTitle(document));
        book.setAuthors(getAuthors(document));
        book.setDescription(getDescription(document));
        book.setPages(getPages(document));
        book.setYear(getYear(document));
        book.setEstimation(getEstimation(document));
        book.setEstimationsCount(getEstimationsCount(document));
        Set<Isbn> isbnEntitySet = getIsbn(document);
        book.setIsbn(isbnEntitySet.stream().findFirst().get().getIsbn());
        book.setIsbns(isbnEntitySet);
        book.setStatus(Book.Status.ACTIVE);
        book.setPublisher(getPublisher(document));
        book.setSeries(getSeries(document));
        book.setCategories(getCategories(document));
        book.setGenre(getGenre(document));
        book.setPrice(0);
        book.setCover(getCover(book));
        return book;
    }

    private Long getLabirintId(Document document) {
        Long labirintId = parseLong(onlyDigits(getElementText(document.selectFirst("div.articul"))));
        log.info("Correct get labirintId: {}", labirintId);
        return labirintId;
    }

    private String getTitle(Document document) {
        String title = getElementText(document.selectFirst("div#product-title > h1"));
        log.info("Correct get title: {}", title);
        return title;
    }

    private Set<Author> getAuthors(Document document) {
        Set<Author> authorsList = new HashSet<>();
        Elements authorElements = document.select("div.authors");
        for (Element authorElement : authorElements) {
            Long authorId = parseLong(onlyDigits(getElementAttribute(authorElement.selectFirst("a"), "abs:href")));
            String[] authorNameAndRole = authorElement.text().split(": ", 2);
            String authorRole = authorNameAndRole[0];
            String authorName = authorNameAndRole[1];
            Author author = new Author();
            author.setLabirintId(authorId);
            author.setName(authorName);
            author.setRole(authorRole);
            authorsList.add(author);
        }
        log.info("Correct get authors list {}", authorsList);
        return authorsList;
    }

    private String getDescription(Document document) {
        Element fullAnnotation = document.getElementById("product-about");
        String description = fullAnnotation == null ? "" : getElementText(fullAnnotation.selectFirst("p"));
        log.info("Correct get description: {}", description);
        return description;
    }

    private Float getEstimation(Document document) {
        Float estimation = parseFloat(getElementText(document.getElementById("rate")));
        log.info("Correct get estimation: {}", estimation);
        return estimation;
    }

    private Integer getEstimationsCount(Document document) {
        Integer estimationCount = parseInt(onlyDigits(getElementText(document.getElementById("product-rating-marks-label"))));
        log.info("Correct get estimationCount: {}", estimationCount);
        return estimationCount;
    }


    private Set<Isbn> getIsbn(Document document) {
        Set<Isbn> isbnEntitySet = new HashSet<>();
        Set<Long> isbns = Arrays.stream(getElementText(document.selectFirst("div.isbn"))
                        .split(" "))
                .map(this::onlyDigits)
                .map(this::parseLong)
                .filter((isbn) -> isbn != 0L)
                .collect(Collectors.toSet());
        for (Long isbn : isbns) {
            Isbn isbnEntity = new Isbn();
            isbnEntity.setIsbn(isbn);
            isbnEntitySet.add(isbnEntity);
        }
        log.info("Correct get isbns: {}", isbns);
        return isbnEntitySet;
    }


    private Integer getPages(Document document) {
        Integer pages = parseInt(getElementAttribute(document.selectFirst("div.pages2 > span"), "data-pages"));
        log.info("Correct get pages: {}", pages);
        return pages;
    }

    private Integer getYear(Document document) {
        Element yearElement = document.selectFirst("div.publisher");
        Integer year = parseInt(onlyDigits(getElementText(yearElement)));
        log.info("Correct get year {}", year);
        return year;
    }

    private Series getSeries(Document document) {
        Series series = new Series();
        Element seriesElement = document.selectFirst("div.series > a");
        String title = getElementText(seriesElement);
        Long seriesId = parseLong(onlyDigits(getElementAttribute(seriesElement, "href")));
        series.setTitle(title);
        series.setLabirintId(seriesId);
        log.info("Correct get series {}", series);
        return series;
    }

    private Publisher getPublisher(Document document) {
        Publisher publisher = new Publisher();
        Element publisherElement = document.selectFirst("div.publisher > a");
        Long publisherId = parseLong(onlyDigits(getElementAttribute(publisherElement, "href")));
        String publisherTitle = getElementText(publisherElement);
        publisher.setLabirintId(publisherId);
        publisher.setTitle(publisherTitle);
        log.info("Correct get publisher {}", publisher);
        return publisher;
    }

    private Set<Category> getCategories(Document document) {
        Set<Category> categoryList = new HashSet<>();
        Elements categoryElements = document.select("div.genre > a");
        for (Element categoryElement : categoryElements) {
            Long categoryId = parseLong(onlyDigits(categoryElement.attr("href")));
            String categoryTitle = categoryElement.attr("data-event-content");
            Category category = new Category();
            category.setLabirintId(categoryId);
            category.setTitle(categoryTitle);
            categoryList.add(category);
        }
        log.info("Correct get category list {}", categoryList);
        return categoryList;
    }

    private Genre getGenre(Document document) {
        String path = getElementText(document.getElementById("thermometer-books"));
        Genre genre = new Genre();
        genre.setPath(path);
        log.info("Correct get genre {}", genre);
        return genre;
    }

    private Long parseLong(String element) {
        try {
            return Long.parseLong(element);
        } catch (NumberFormatException numberFormatException) {
            log.warn("Fail to parseLong element: {}", element);
            return 0L;
        }
    }

    private Integer parseInt(String element) {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException numberFormatException) {
            log.warn("Fail to parseInt element: {}", element);
            return 0;
        }
    }

    private Float parseFloat(String element) {
        try {
            return Float.parseFloat(element);
        } catch (NumberFormatException numberFormatException) {
            log.warn("Fail to parseFloat element: {}", element);
            return 0F;
        }
    }

    private String onlyDigits(String element) {
        return element.replaceAll("\\D+", "");
    }

    private String getElementText(Element element) {
        return element == null ? "" : element.text();
    }

    private String getElementAttribute(Element element, String attribute) {
        return element == null ? "" : element.attr(attribute);
    }

    // TODO: 14.01.2023 refactor it when the path will be more complex
    private Cover getCover(Book book) {
        Cover cover = new Cover();
        cover.setPath(book.getLabirintId().toString());
        cover.setExtension("jpg");
        return cover;
    }

}
