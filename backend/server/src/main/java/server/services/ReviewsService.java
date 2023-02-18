package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entities.Review;
import server.repositories.ReviewsRepository;

import java.util.List;

@Service
public class ReviewsService {
    private ReviewsRepository reviewsRepository;

    @Autowired
    public void setReviewsRepository(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public List<Review> getBookReviews(Long isbn) {
        return reviewsRepository.findAllByBook_Isbn(isbn);
    }

    public List<Review> getUserReviews(Long id) {
        return reviewsRepository.findAllByUser_Id(id);
    }

    public Review saveOrUpdate(Review review) {
        return reviewsRepository.save(review);
    }
}
