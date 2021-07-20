package backend.controllers;

import backend.entities.Book;
import backend.entities.dtos.BookDto;
import backend.exceptions.BookNotFoundException;
import backend.services.BooksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*@Rest подставляет в каждый метод @ResponseBody*/
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
@Api("Set of endpoints for CRUD operations for Books")
public class RestBooksController {

    private BooksService booksService;

    @Autowired
    public RestBooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    @ApiOperation("")
    public List<Book> getAllBooks() {
        return booksService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one of books by id")
    public Book getOneBook(@PathVariable @ApiParam("Id of the book to be requested. Can not be empty") Long id) {
        if (!booksService.existsById(id)) {
            throw new BookNotFoundException("Book not found, id: " + id);
        }
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
    public Book saveNewBook(@RequestBody Book book) {
        if (book.getId() != null) {
            book.setId(null);
        }
        return booksService.saveOrUpdate(book);
    }

    /*ResponseEntity - чтобы помимо объекта вернуть, например, статус-код*/
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing book")
    public ResponseEntity<?> modifyBook(@RequestBody Book book) {
        if (book.getId() == null || booksService.existsById(book.getId())) {
            throw new BookNotFoundException("Book not found, id: " + book.getId());
        }
        if (book.getPrice() < 0) {
            return new ResponseEntity<>("Book's price can not be negative", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(booksService.saveOrUpdate(book), HttpStatus.OK);
    }

    /*Перехватывает исключения из сигнатуры и оборачивает в ResponseEntity*/
    @ExceptionHandler
    public ResponseEntity<?> handleException(BookNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
