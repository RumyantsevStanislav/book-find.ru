package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.Publisher;

@Repository
public interface PublishersRepository extends JpaRepository<Publisher, Long>, JpaSpecificationExecutor<Publisher> {
    Publisher findByTitle(String title);
}

