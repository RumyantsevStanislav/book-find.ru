package server.entities.dtos;

import java.util.Set;

public record BookDtoFullImpl(String isbn,
                              String title,
                              Set<AuthorDto> authors,
                              CoverDto cover,
                              Float estimation,
                              String description,
                              Integer price,
                              Integer pages,
                              Integer year,
                              Set<CategoryDto> categories,
                              PublisherDto publisher,
                              GenreDto genre,
                              SeriesDto series,
                              Set<BookReviewsDto> reviews) implements BookDtoFull {
    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    @Override
    public CoverDto getCover() {
        return cover;
    }

    @Override
    public Float getEstimation() {
        return estimation;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public Integer getPages() {
        return pages;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public Set<CategoryDto> getCategories() {
        return categories;
    }

    @Override
    public PublisherDto getPublisher() {
        return publisher;
    }

    @Override
    public GenreDto getGenre() {
        return genre;
    }

    @Override
    public SeriesDto getSeries() {
        return series;
    }

    @Override
    public Set<BookReviewsDto> getReviews() {
        return reviews;
    }
}
