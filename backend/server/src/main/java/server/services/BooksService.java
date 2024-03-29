package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.entities.Book;
import server.entities.dtos.BookDto;
import server.entities.dtos.BookDtoImpl;
import server.mappers.BookMapper;
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

    public List<Book> getByTitle(String title) {
        return booksRepository.findByTitle(title);
    }

    public Optional<BookDto> getByIsbn(Long isbn) {
        return booksRepository.findByIsbn(isbn);
    }

    public void deleteByIsbn(Long isbn) {
        booksRepository.deleteByIsbn(isbn);
    }

    public boolean existsById(Long id) {
        return booksRepository.existsById(id);
    }

    public Page<BookDtoImpl> getPageDto(Specification<Book> spec, int page, int size) {
        Page<Book> bookPage = booksRepository.findAll(spec, PageRequest.of(page, size));
        return bookPage.map(BookMapper.BOOK_MAPPER::toDto);
    }
}
