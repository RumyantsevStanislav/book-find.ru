package server.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import server.entities.Book;
import server.utils.TestBooks;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

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
    public void initDbTest() {
        List<Book> allBooks = booksRepository.findAll();
        Assertions.assertEquals(2, allBooks.size());
    }
}
