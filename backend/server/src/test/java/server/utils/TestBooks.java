package server.utils;

import server.entities.*;

import java.util.Set;

public class TestBooks {
    public static final String
            BOOK_TITLE = "BookTitle1",
            BOOK_DESCRIPTION = "BookDescription1",
            AUTHOR_ROLE = "Автор",
            TRANSLATOR_ROLE = "Переводчик",
            AUTHOR_NAME = "Author1",
            TRANSLATOR_NAME = "Translator1",
            CATEGORY_TITLE_1 = "CategoryTitle1.1",
            CATEGORY_TITLE_2 = "CategoryTitle1.2",
            COVER_EXTENSION = "jpg",
            COVER_PATH = "1",
            GENRE_PATH = "Path1",
            PUBLISHER_TITLE = "PublisherTitle1",
            PUBLISHER_DESCRIPTION = "PublisherDescription1",
            SERIES_TITLE = "SeriesTitle1",
            SERIES_DESCRIPTION = "SeriesDescription1";


    public static final int
            PRICE = 0,
            PAGES = 101,
            YEAR = 2021;
    public static final long ISBN = 9785907143784L;
    public static final float ESTIMATION = 8.6f;

    public static Set<Author> getAuthors() {
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        author.setRole(AUTHOR_ROLE);
        Author translator = new Author();
        translator.setName(TRANSLATOR_NAME);
        translator.setRole(TRANSLATOR_ROLE);
        return Set.of(author, translator);
    }

    public static Set<Category> getCategories() {
        Category firstCategory = new Category();
        firstCategory.setTitle(CATEGORY_TITLE_1);
        Category secondCategory = new Category();
        secondCategory.setTitle(CATEGORY_TITLE_2);
        return Set.of(firstCategory, secondCategory);
    }

    public static Cover getCover() {
        Cover cover = new Cover();
        cover.setExtension(COVER_EXTENSION);
        cover.setPath(COVER_PATH);
        return cover;
    }

    public static Genre getGenre() {
        Genre genre = new Genre();
        genre.setPath(GENRE_PATH);
        return genre;
    }

    public static Publisher getPublisher() {
        Publisher publisher = new Publisher();
        publisher.setTitle(PUBLISHER_TITLE);
        publisher.setDescription(PUBLISHER_DESCRIPTION);
        return publisher;
    }

    public static Series getSeries() {
        Series series = new Series();
        series.setTitle(SERIES_TITLE);
        series.setDescription(SERIES_DESCRIPTION);
        return series;
    }

    public static Book getBook() {
        Book book = new Book();
        book.setTitle(BOOK_TITLE);
        book.setDescription(BOOK_DESCRIPTION);
        book.setPrice(PRICE);
        book.setPages(PAGES);
        book.setYear(YEAR);
        book.setEstimation(ESTIMATION);
        book.setIsbn(ISBN);
        book.setAuthors(getAuthors());
        book.setCategories(getCategories());
        book.setStatus(Book.Status.ACTIVE);
        book.setPublisher(getPublisher());
        book.setGenre(getGenre());
        book.setSeries(getSeries());
        book.setCover(getCover());
        return book;
    }
}
