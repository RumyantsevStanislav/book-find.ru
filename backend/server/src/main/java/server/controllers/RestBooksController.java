package server.controllers;

import server.entities.*;
import server.entities.dtos.BookDto;
import server.exceptions.BookNotFoundException;
import server.exceptions.InfoResponse;
import server.services.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController /*@Rest подставляет в каждый метод @ResponseBody*/
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
@Api("Set of endpoints for CRUD operations for Books")
public class RestBooksController {

    private final BooksService booksService;
    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final PublishersService publishersService;
    private final CategoriesService categoriesService;
    private final SeriesService seriesService;

    @Autowired
    public RestBooksController(BooksService booksService, AuthorsService authorsService, GenresService genresService, PublishersService publishersService, CategoriesService categoriesService, SeriesService seriesService) {
        this.booksService = booksService;
        this.authorsService = authorsService;
        this.genresService = genresService;
        this.publishersService = publishersService;
        this.categoriesService = categoriesService;
        this.seriesService = seriesService;
    }

    @GetMapping
    @ApiOperation("")
    public List<Book> getAllBooks() {
        return booksService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one of books by id")
    public Book getOneBook(@PathVariable @ApiParam("Id of the book to be requested. Can not be empty") Long id) {
        return booksService.findById(id);
    }

    @GetMapping("/dto")
    @ApiOperation("Returns list of all books data transfer objects")
    public List<BookDto> getAllBooksDto() {
        return booksService.getDtoData();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("")
    public String deleteOneBook(@PathVariable Long id) {
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

    /*ResponseEntity - чтобы помимо объекта вернуть, например, статус-код*/
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing book")
    public ResponseEntity<InfoResponse> modifyBook(@RequestBody Book book) {
        if (book.getId() == null || booksService.existsById(book.getId())) {
            throw new BookNotFoundException("Book not found, id: " + book.getId());
        }
        if (book.getPrice() < 0) {
            return new ResponseEntity<>(new InfoResponse("Book's price can not be negative"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new InfoResponse("Успешно изменено"), HttpStatus.OK);
    }

    private void checkBook(Book book) {
        if (book.getId() != null) {
            book.setId(null);
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
