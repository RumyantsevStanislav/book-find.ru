package server.entities.dtos;

public interface UserReviewDto extends ReviewDto {
    BookDto getBook();
}
