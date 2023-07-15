package server.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import server.entities.*;
import server.entities.dtos.BookDtoFull;
import server.entities.dtos.BookDtoImpl;
import server.entities.dtos.api.ApiMessage;
import server.exceptions.AttributeNotValidException;
import server.exceptions.BookNotFoundException;
import server.exceptions.ElementAlreadyExistsException;
import server.services.*;
import server.utils.BookFilter;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
@Api("Set of endpoints for CRUD operations for Books")
@AllArgsConstructor
public class BooksController {

    private final BooksService booksService;
    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final PublishersService publishersService;
    private final CategoriesService categoriesService;
    private final SeriesService seriesService;
    private final UsersService usersService;

    @GetMapping(value = "/{isbn}", produces = "application/json")
    @ApiOperation("Returns one book by isbn.")
    public ResponseEntity<BookDtoFull> getOneBook(@PathVariable @ApiParam("ISBN of the book to be requested. Can not be empty") @NotNull String isbn, Principal principal) {
        /// TODO: 18.02.2023 add personal book if user is authorized
        return new ResponseEntity<>(booksService.getDtoFullByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Can't find book with isbn = " + isbn)), HttpStatus.OK);
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @DeleteMapping("/{isbn}")
    @ApiOperation("")
    public void deleteOneBook(@PathVariable @NotNull Long isbn) {
        booksService.deleteByIsbn(isbn);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")/*описываем что ожидаем и что возвращаем*/
    @ApiOperation("")
    @ResponseStatus(HttpStatus.CREATED) /* Возвращаем статус. Альтернатива ResponseEntity */
    public void saveNewBook(@RequestBody Book book) {
        checkBook(book);
        checkAuthors(book);
        checkGenre(book);
        checkPublisher(book);
        checkCategories(book);
        checkSeries(book);
        for (Isbn isbn : book.getIsbns()) {
            isbn.setBook(book);
        }
        try {
            booksService.saveOrUpdate(book);
        } catch (DataIntegrityViolationException exception) {
            System.out.println("Posgres error");
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing book")
    public ResponseEntity<ApiMessage> modifyBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (book.getId() == null || booksService.existsById(book.getId())) {
            throw new BookNotFoundException("Book not found, id: " + book.getId());
        }
        if (book.getPrice() < 0) {
            throw new AttributeNotValidException("Book's price can not be negative", bindingResult);
        }
        return new ResponseEntity<>(new ApiMessage("Успешно изменено"), HttpStatus.CREATED);
    }

    @ApiOperation("Returns list of all books data transfer objects")
    @GetMapping(value = "", produces = "application/json")
    public Page<BookDtoImpl> showAll(@RequestParam Map<String, String> requestParams,
                                     @RequestParam(name = "categories", required = false) List<Long> categoriesIds,
                                     @RequestParam(name = "s") int size,
                                     Principal principal) {
        List<Category> categoriesFilter = null;
        if (categoriesIds != null) {
            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
        }
        int pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "0"));
        BookFilter bookFilter = new BookFilter(requestParams, categoriesFilter);
        if (principal != null) {
            User user = usersService.getUserByPhoneOrEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не существует"));
            //return booksService.getPageDtoWithPersonalBook(bookFilter.getSpec(), pageNumber, size, user.getPhone(), user.getEmail());
        }
        return booksService.getPageDto(bookFilter.getSpec(), pageNumber, size);
    }

    private void checkBook(Book book) {
        if (book.getId() != null) {
            book.setId(null);
        }
        String isbn = book.getIsbn();
        if (booksService.getByIsbn(isbn).isPresent()) {
            throw new ElementAlreadyExistsException("Book with isbn " + isbn + " is already exist");
        }
    }

    //@Transactional(isolation = Isolation.SERIALIZABLE)
    //@Retryable
    private void checkAuthors(Book book) {
        Set<Author> currentAuthors = book.getAuthors();
        Set<Author> authors = new HashSet<>(currentAuthors);
        for (Author author : authors) {
            String name = author.getName();
            String role = author.getRole();
            Author existingAuthor = authorsService.findByNameAndRole(name, role);
            if (existingAuthor != null) {
                currentAuthors.add(existingAuthor);
                currentAuthors.remove(author);
            } /*else {
                authorsService.saveOrUpdate(author);
            }*/
        }
    }

    private void checkGenre(Book book) {
        Genre genre = book.getGenre();
        String path = genre.getPath();
        Genre existingGenre = genresService.findByPath(path);
        if (existingGenre != null) {
            book.setGenre(existingGenre);
        }
    }

    private void checkPublisher(Book book) {
        Publisher publisher = book.getPublisher();
        String title = publisher.getTitle();
        Publisher existingPublisher = publishersService.findByTitle(title);
        if (existingPublisher != null) {
            book.setPublisher(existingPublisher);
        }
    }

    private void checkCategories(Book book) {
        Set<Category> currentCategories = book.getCategories();
        Set<Category> categories = new HashSet<>(currentCategories);
        for (Category category : categories) {
            String title = category.getTitle();
            Category existingCategory = categoriesService.findByTitle(title);
            if (existingCategory != null) {
                currentCategories.add(existingCategory);
                currentCategories.remove(category);
            }
        }
    }

    private void checkSeries(Book book) {
        Series series = book.getSeries();
        String title = series.getTitle();
        Series existingSeries = seriesService.findByTitle(title);
        if (existingSeries != null) {
            book.setSeries(existingSeries);
        }
    }
}
