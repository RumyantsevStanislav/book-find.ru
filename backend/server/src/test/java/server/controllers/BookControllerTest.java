package server.controllers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import server.configs.JwtRequestFilter;
import server.entities.Book;
import server.entities.dtos.BookDto;
import server.entities.dtos.BookDtoFull;
import server.services.*;
import server.utils.TestBooks;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BooksController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unsecured")
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BooksService bookService;
    @MockBean
    private AuthorsService authorsService;
    @MockBean
    private GenresService genresService;
    @MockBean
    private PublishersService publishersService;
    @MockBean
    private CategoriesService categoriesService;
    @MockBean
    private SeriesService seriesService;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Test
    public void getBookDtoByIsbn() throws Exception {
        Book book = TestBooks.getBook();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        BookDtoFull bookDtoFull = factory.createProjection(BookDtoFull.class, book);
        given(bookService.getDtoFullByIsbn(TestBooks.ISBN)).willReturn(Optional.of(bookDtoFull));
        mvc.perform(get("/api/v1/books/" + TestBooks.ISBN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(TestBooks.BOOK_TITLE)));

        given(bookService.getDtoFullByIsbn(TestBooks.ISBN)).willReturn(Optional.empty());
        Assertions.assertThrows(Exception.class,
                () -> mvc.perform(get("/api/v1/books/" + TestBooks.ISBN)
                        .contentType(MediaType.APPLICATION_JSON)));
        // TODO: 17.11.2022 figure out
        //.andExpect(jsonPath("$", hasSize(1)))
        //.andExpect(jsonPath("$").isArray())
        //.andExpect(jsonPath("$[0].title", is(allBooks.get(0).getTitle())));
    }
}
