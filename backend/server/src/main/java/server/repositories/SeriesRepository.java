package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.Series;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long>, JpaSpecificationExecutor<Series> {
    Series findByTitle(String title);
}

