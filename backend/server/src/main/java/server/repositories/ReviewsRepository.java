package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import server.entities.Review;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    List<Review> findAllByBook_Isbn(Long isbn);

    List<Review> findAllByUser_Id(Long id);
}
