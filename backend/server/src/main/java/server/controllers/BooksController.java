package server.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import server.entities.*;
import server.entities.dtos.ApiMessage;
import server.entities.dtos.BookDto;
import server.exceptions.AttributeNotValidException;
import server.exceptions.BookNotFoundException;
import server.exceptions.ElementAlreadyExistsException;
import server.services.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
@Api("Set of endpoints for CRUD operations for Books")
public class BooksController {

    private final BooksService booksService;
    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final PublishersService publishersService;
    private final CategoriesService categoriesService;
    private final SeriesService seriesService;

    @Autowired
    public BooksController(BooksService booksService, AuthorsService authorsService, GenresService genresService, PublishersService publishersService, CategoriesService categoriesService, SeriesService seriesService) {
        this.booksService = booksService;
        this.authorsService = authorsService;
        this.genresService = genresService;
        this.publishersService = publishersService;
        this.categoriesService = categoriesService;
        this.seriesService = seriesService;
    }

    @GetMapping(value = "/{isbn}", produces = "application/json")
    @ApiOperation("Returns one of books by isbn")
    public Book getOneBook(@PathVariable @ApiParam("ISBN of the book to be requested. Can not be empty") @NotNull Long isbn) {
        return booksService.getByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Can't find book with isbn = " + isbn));
    }

    @GetMapping("/dto")
    @ApiOperation("Returns list of all books data transfer objects")
    public List<BookDto> getAllBooksDto() {
        return booksService.getDtoData();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("")
    public String deleteOneBook(@PathVariable @NotNull Long id) {
        booksService.deleteById(id);
        return "OK";
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
        try {
            booksService.saveOrUpdate(book);
        } catch (RuntimeException runtimeException) {
            //do nothing
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


    //    @GetMapping(value = "/books", produces = "application/json")
    //    Page<BookDto> getAllBooks(@RequestParam(defaultValue = "1", name = "p") Integer page,
    //                              @RequestParam Map<String, String> params,
    //                              @RequestParam (name = "s") int size);

    //    @GetMapping
    //    public String showAll(Model model, @RequestParam Map<String, String> requestParams, @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
    //        List<Category> categoriesFilter = null;
    //        if (categoriesIds != null) {
    //            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
    //        }
    //        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
    //        BookFilter bookFilter = new BookFilter(requestParams, categoriesFilter);
    //        Page<Book> books = booksService.getAll(bookFilter.getSpec(), pageNumber);
    //        model.addAttribute("books", books);
    //        model.addAttribute("filterDef", bookFilter.getFilterDefinition().toString());
    //        return "all_books";
    //    }

    private void checkBook(Book book) {
        if (book.getId() != null) {
            book.setId(null);
        }
        Long isbn = book.getIsbn();
        if (booksService.getByIsbn(isbn).isPresent()) {
            throw new ElementAlreadyExistsException("Book with isbn " + isbn + " is already exist");
        }
    }

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
            }
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
