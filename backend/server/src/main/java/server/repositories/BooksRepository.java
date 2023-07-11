package server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
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
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>/*, ElasticsearchRepository<Book, Long>*/ {
    List<Book> findByTitle(String title);

    Optional<BookDtoFull> findDtoFullByIsbn(Long isbn);

    Optional<Book> findByIsbn(Long isbn);

    void deleteByIsbn(Long isbn);

    List<BookDto> findAllBy();

    @Query(value = "select books.*," +
            " authors.name, authors.role," +
            " covers.path, covers.extension," +
            " personal_book.status, personal_book.estimation, personal_book.comment" +
            " from books " +
            "left join books_authors on books.id = books_authors.book_id" +
            " left join authors on books_authors.author_id = authors.id" +
            " left join covers on covers.id = books.cover_id " +
            "left join " +
            "(select status, estimation, comment, isbn from personal_books where phone = ?1 or email = ?2)" +
            " as personal_book on books.isbn = personal_book.isbn",
            nativeQuery = true)
    Page<BookDto> findAllWithPersonalBook(String phone, String email, Pageable pageable);

    // TODO: 17.11.2022 research how to optimize all requests

    //    @Query("select b.id as id, b.title as title, b.genre as genre, b.description as description, b.price as price, b.publishYear as publishYear, b.author.name as authorName from Book b")
    //    List<BookDto> findAllBooksWithAuthorName();

}
