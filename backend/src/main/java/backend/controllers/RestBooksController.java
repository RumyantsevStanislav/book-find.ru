package backend.controllers;

import backend.entities.Book;
import backend.entities.dtos.BookDto;
import backend.exceptions.BookNotFoundException;
import backend.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*@Rest подставляет в каждый метод @ResponseBody*/
@RequestMapping("/api/v1/books")
public class RestBooksController {

    private BooksService booksService;

    @Autowired
    public RestBooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return booksService.findAll();
    }

    @GetMapping("/{id}")
    public Book getOneBook(@PathVariable Long id) {
        return booksService.findById(id);
    }

    @GetMapping("/dto")
    public List<BookDto> getAllBooksDto(){
        return booksService.getDtoData();
    }

    @DeleteMapping("/{id}")
    public String deleteOneBook(@PathVariable Long id) {
        booksService.deleteById(id);
        return "OK";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) /* Возвращаем статус. Альтернатива ResponseEntity */
    public Book saveNewBook(@RequestBody Book book) {
        if (book.getId() != null) {
            book.setId(null);
        }
        return booksService.saveOrUpdate(book);
    }
    /*ResponseEntity - чтобы помимо объекта вернуть, например, статус-код*/
    @PutMapping
    public ResponseEntity<?> modifyBook(@RequestBody Book book) {
        if (book.getId() == null || booksService.existsById(book.getId())) {
            throw new BookNotFoundException("Book not found, id: " + book.getId());
        }
        if (book.getPrice() < 0){
            return  new ResponseEntity<>("Book's price can not be negative", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(booksService.saveOrUpdate(book), HttpStatus.OK);
    }
    /*Перехватывает исключения из сигнатуры и оборачивает в ResponseEntity*/
    @ExceptionHandler
    public ResponseEntity<?> handleException(BookNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
