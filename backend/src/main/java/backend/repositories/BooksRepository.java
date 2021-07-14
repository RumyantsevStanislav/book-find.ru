package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import backend.entities.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

}
