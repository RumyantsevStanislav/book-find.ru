package server.entities.dtos;

import java.util.Set;

public interface BookDtoFull extends BookDto {

    String getDescription();

    Integer getPrice();

    Integer getPages();

    Integer getYear();

    Set<CategoryDto> getCategories();

    PublisherDto getPublisher();

    GenreDto getGenre();

    SeriesDto getSeries();

    Set<BookReviewsDto> getReviews();
}
