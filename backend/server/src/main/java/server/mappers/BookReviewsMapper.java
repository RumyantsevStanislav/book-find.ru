package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import server.entities.Review;
import server.entities.dtos.BookReviewDtoImpl;

import java.util.List;

@Mapper
public interface BookReviewsMapper {
    BookReviewsMapper REVIEWS_MAPPER = Mappers.getMapper(BookReviewsMapper.class);

    BookReviewDtoImpl toDto(Review review);

    List<BookReviewDtoImpl> toDtoList(List<Review> reviews);
}
