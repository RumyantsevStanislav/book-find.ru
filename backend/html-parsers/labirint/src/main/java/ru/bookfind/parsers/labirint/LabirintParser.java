package ru.bookfind.parsers.labirint;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
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
        book.setIsbn(isbnEntitySet.stream().findFirst().map(Isbn::getIsbn).orElse("labirintId: " + getLabirintId(document)));
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
        log.debug("Correct get labirintId: {}", labirintId);
        return labirintId;
    }

    private String getTitle(Document document) {
        String title = getElementText(document.selectFirst("div#product-title > h1"));
        log.debug("Correct get title: {}", title);
        return title;
    }

    private Set<Author> getAuthors(Document document) {
        Set<Author> authorsList = new HashSet<>();
        Elements authorsElements = document.select("div.authors");
        for (Element authorsElement : authorsElements) {
            String authorsRole = authorsElement.text().split(": ", 2)[0];
            Elements authors = authorsElement.select("a");
            for (Element authorElement : authors) {
                Long authorId = parseLong(onlyDigits(getElementAttribute(authorElement, "abs:href")));
                String authorName = getElementText(authorElement);
                Author author = new Author();
                author.setLabirintId(authorId);
                author.setName(authorName);
                author.setRole(authorsRole);
                authorsList.add(author);
            }
        }
        log.debug("Correct get authors list {}", authorsList);
        return authorsList;
    }

    private String getDescription(Document document) {
        Element fullAnnotation = document.getElementById("product-about");
        String description = fullAnnotation == null ? "" : getElementText(fullAnnotation.selectFirst("p"));
        log.debug("Correct get description: {}", description);
        return description;
    }

    private Float getEstimation(Document document) {
        Float estimation = parseFloat(getElementText(document.getElementById("rate")));
        log.debug("Correct get estimation: {}", estimation);
        return estimation;
    }

    private Integer getEstimationsCount(Document document) {
        Integer estimationCount = parseInt(onlyDigits(getElementText(document.getElementById("product-rating-marks-label"))));
        log.debug("Correct get estimationCount: {}", estimationCount);
        return estimationCount;
    }


    private Set<Isbn> getIsbn(Document document) {
        Set<Isbn> isbnEntitySet = new HashSet<>();
        Set<String> isbns = Arrays.stream(getElementText(document.selectFirst("div.isbn"))
                        .split(" "))
                .map(this::onlyDigitsLetters)
                .filter(StringUtils::hasText)
                .filter(isbn -> isbn.matches(".*\\d+.*"))
                .collect(Collectors.toSet());
        for (String isbn : isbns) {
            Isbn isbnEntity = new Isbn();
            isbnEntity.setIsbn(isbn);
            isbnEntitySet.add(isbnEntity);
        }
        log.debug("Correct get isbns: {}", isbns);
        return isbnEntitySet;
    }


    private Integer getPages(Document document) {
        Integer pages = parseInt(getElementAttribute(document.selectFirst("div.pages2 > span"), "data-pages"));
        log.debug("Correct get pages: {}", pages);
        return pages;
    }

    private Integer getYear(Document document) {
        Element yearElement = document.selectFirst("div.publisher");
        Integer year = parseInt(onlyDigits(getElementText(yearElement)));
        log.debug("Correct get year {}", year);
        return year;
    }

    private Series getSeries(Document document) {
        Series series = new Series();
        Element seriesElement = document.selectFirst("div.series > a");
        String title = getElementText(seriesElement);
        Long seriesId = parseLong(onlyDigits(getElementAttribute(seriesElement, "href")));
        series.setTitle(title);
        series.setLabirintId(seriesId);
        log.debug("Correct get series {}", series);
        return series;
    }

    private Publisher getPublisher(Document document) {
        Publisher publisher = new Publisher();
        Element publisherElement = document.selectFirst("div.publisher > a");
        Long publisherId = parseLong(onlyDigits(getElementAttribute(publisherElement, "href")));
        String publisherTitle = getElementText(publisherElement);
        publisher.setLabirintId(publisherId);
        publisher.setTitle(publisherTitle);
        log.debug("Correct get publisher {}", publisher);
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
        log.debug("Correct get category list {}", categoryList);
        return categoryList;
    }

    private Genre getGenre(Document document) {
        String path = getElementText(document.getElementById("thermometer-books"));
        Genre genre = new Genre();
        genre.setPath(path);
        log.debug("Correct get genre {}", genre);
        return genre;
    }

    private Long parseLong(String element) {
        try {
            return Long.parseLong(element);
        } catch (NumberFormatException numberFormatException) {
            log.debug("Fail to parseLong element: {}", element);
            return 0L;
        }
    }

    private Integer parseInt(String element) {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException numberFormatException) {
            log.debug("Fail to parseInt element: {}", element);
            return 0;
        }
    }

    private Float parseFloat(String element) {
        try {
            return Float.parseFloat(element);
        } catch (NumberFormatException numberFormatException) {
            log.debug("Fail to parseFloat element: {}", element);
            return 0F;
        }
    }

    private String onlyDigits(String element) {
        return element.replaceAll("\\D+", "");
    }

    private String onlyDigitsLetters(String element) {
        return element.replaceAll("[^0-9а-яА-ЯёЁa-zA-Z]+", "");
    }

    private String removeDash(String element) {
        return element.replaceAll("-", "");
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
