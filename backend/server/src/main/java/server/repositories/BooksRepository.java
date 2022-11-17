package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.entities.Book;
import server.entities.dtos.BookDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByTitle(String title);

    Optional<BookDto> findByIsbn(Long isbn);

    void deleteByIsbn(Long isbn);

    List<BookDto> findAllBy();

    // TODO: 17.11.2022 research how to optimize all requests
    @Query("select b.id as id, b.title as title, b.genre as genre, b.description as description, b.price as price, b.publishYear as publishYear, b.author.name as authorName from Book b")
    List<BookDto> findAllBooksWithAuthorName();

}
