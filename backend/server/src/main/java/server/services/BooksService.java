package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.entities.Book;
import server.entities.dtos.BookDto;
import server.exceptions.BookNotFoundException;
import server.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private BooksRepository booksRepository;

    @Autowired
    public void setBooksRepository(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Book saveOrUpdate(Book book) {
        return booksRepository.save(book);
    }

    public Book findById(Long id) {
        return booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Can't find book with id = " + id));
    }

    public List<Book> getByTitle(String title) {
        return booksRepository.findByTitle(title);
    }

    public Optional<Book> getByIsbn(Long isbn) {
        return booksRepository.findByIsbn(isbn);
    }

    //@Secured(роли)
    public Page<Book> getAll(Specification<Book> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return booksRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public void deleteById(Long id) {
        booksRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return booksRepository.existsById(id);
    }

    public List<BookDto> getDtoData() {
        return booksRepository.findAllBy();
    }

    public Page<Book> findAll(Specification<Book> spec, int page, int size) {
        return booksRepository.findAll(spec, PageRequest.of(page, size));
    }
}
