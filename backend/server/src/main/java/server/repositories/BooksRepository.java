package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.entities.Book;
import server.entities.dtos.BookDto;
import server.entities.dtos.BookDtoFull;

import java.util.List;
import java.util.Optional;

// TODO: 25.01.2023 is the Repository annotation need to be here?  Spring data interfaces includes Repository?
//@Repository
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByTitle(String title);

    Optional<BookDtoFull> findByIsbn(Long isbn);

    void deleteByIsbn(Long isbn);

    List<BookDto> findAllBy();

    // TODO: 17.11.2022 research how to optimize all requests

    //    @Query("select b.id as id, b.title as title, b.genre as genre, b.description as description, b.price as price, b.publishYear as publishYear, b.author.name as authorName from Book b")
    //    List<BookDto> findAllBooksWithAuthorName();

}
