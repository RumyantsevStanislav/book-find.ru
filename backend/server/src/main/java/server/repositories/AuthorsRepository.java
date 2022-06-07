package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Author findByNameAndRole(String name, String role);
}

