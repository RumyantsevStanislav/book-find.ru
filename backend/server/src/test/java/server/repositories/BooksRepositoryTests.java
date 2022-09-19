package server.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import server.entities.Book;
import server.entities.Category;
import server.utils.BookFilter;
import server.utils.TestBooks;

import javax.persistence.PersistenceException;
import java.util.*;

@DataJpaTest
@ActiveProfiles("test")
public class BooksRepositoryTests {
    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void bookRepositoryTest() {
        Optional<Book> book = booksRepository.findByIsbn(TestBooks.ISBN);
        Assertions.assertTrue(book.isPresent());

        List<Book> bookList = booksRepository.findByTitle(TestBooks.BOOK_TITLE);
        Assertions.assertEquals(1, bookList.size());

        booksRepository.deleteByIsbn(TestBooks.ISBN);
        List<Book> allBooks = booksRepository.findAll();
        Assertions.assertEquals(1, allBooks.size());

        Book existingBook = TestBooks.getBook();
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(existingBook));
    }

    @Test
    public void getPageBooksTest() {
        Map<String, String> parameters = Map.of("min_price", "0");
        List<Category> categories = TestBooks.getCategories().stream().toList();
        BookFilter bookFilter = new BookFilter(parameters, null);
        Page<Book> bookPage = booksRepository.findAll(bookFilter.getSpec(), PageRequest.of(0, 1));
        Assertions.assertEquals(bookPage.getTotalElements(), 2);
        Assertions.assertEquals(bookPage.getTotalPages(), 2);
        Assertions.assertEquals(bookPage.getContent().size(), 1);
    }

    @Test
    public void initDbTest() {
        List<Book> allBooks = booksRepository.findAll();
        Assertions.assertEquals(2, allBooks.size());
    }
}
