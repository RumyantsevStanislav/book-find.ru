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
import server.entities.dtos.BookDto;
import server.entities.dtos.BookDtoFull;
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
    private CategoriesRepository categoriesRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void bookRepositoryTest() {
        Optional<BookDtoFull> bookDtoFull = booksRepository.findDtoFullByIsbn(TestBooks.ISBN);
        Assertions.assertTrue(bookDtoFull.isPresent());

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
        List<Category> categories = Collections.singletonList(categoriesRepository.findByTitle(TestBooks.CATEGORY_TITLE_1));
        BookFilter bookFilter = new BookFilter(parameters, categories);
        Page<Book> bookPage = booksRepository.findAll(bookFilter.getSpec(), PageRequest.of(0, 1));
        Assertions.assertEquals(bookPage.getTotalElements(), 1);
        Assertions.assertEquals(bookPage.getTotalPages(), 1);
        Assertions.assertEquals(bookPage.getContent().size(), 1);
    }

    @Test
    public void initDbTest() {
        List<Book> allBooks = booksRepository.findAll();
        Assertions.assertEquals(2, allBooks.size());
    }
    //    Statistics stats = sessionFactory.getStatistics();
    //stats.setStatisticsEnabled(true);
    //    stats.getSessionOpenCount();
    //stats.logSummary();
    //    @TestPropertySource(properties = [
    //            "spring.jpa.properties.hibernate.generate_statistics=true",
    //            "logging.level.org.hibernate.stat=debug"
    //            ])
}
