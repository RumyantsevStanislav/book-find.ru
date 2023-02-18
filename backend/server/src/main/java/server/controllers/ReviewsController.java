package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import server.entities.Review;
import server.entities.User;
import server.entities.dtos.api.ApiMessage;
import server.exceptions.BookNotFoundException;
import server.services.BooksService;
import server.services.ReviewsService;
import server.services.UsersService;

import java.security.Principal;

@RestController
// TODO: 17.11.2022 remove on production
@CrossOrigin("*")
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    private final UsersService usersService;
    private final BooksService booksService;

    @Autowired
    ReviewsController(ReviewsService reviewsService, UsersService usersService, BooksService booksService) {
        this.reviewsService = reviewsService;
        this.usersService = usersService;
        this.booksService = booksService;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Review> getPersonalBooks(@RequestBody Review unsavedReview, Principal principal) {
        User user = usersService.getUserByPhoneOrEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        unsavedReview.setUser(user);
        unsavedReview.setBook(booksService.getByIsbn(unsavedReview.getBook().getIsbn()).orElseThrow(() -> new BookNotFoundException("Book not found")));
        Review review = reviewsService.saveOrUpdate(unsavedReview);
        return ResponseEntity.ok(review);

    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiMessage> changeReview(Principal principal) {
        return ResponseEntity.ok(new ApiMessage("Ваша рецензия успешно изменена."));
    }

    @DeleteMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiMessage> deleteReview(Principal principal) {
        return ResponseEntity.ok(new ApiMessage("Ваша рецензия успешно удалена."));
    }
}
