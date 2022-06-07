package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.Genre;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
    Genre findByPath(String path);
}

