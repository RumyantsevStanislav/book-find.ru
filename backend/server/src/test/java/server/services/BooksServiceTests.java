package server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import server.entities.Book;
import server.repositories.BooksRepository;
import server.utils.TestBooks;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTests {
    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    @BeforeEach
    void setUp() {
        Book bookFromDb = TestBooks.getBook();
        Mockito.lenient().doReturn(Collections.singletonList(bookFromDb)).when(booksRepository).findByTitle(TestBooks.BOOK_TITLE);
    }

    @Test
    @DisplayName("Success get book by title.")
    public void getByTitleTest() {
        List<Book> books;
        books = booksService.getByTitle(TestBooks.BOOK_TITLE);
        Assertions.assertEquals(books.size(), 1);
        Mockito.verify(booksRepository, Mockito.times(1)).findByTitle(eq(TestBooks.BOOK_TITLE));
    }
}
