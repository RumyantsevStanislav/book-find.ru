package server.entities.dtos;

import java.util.Set;

public record BookDtoImpl(String isbn, String title, Set<AuthorDto> authors, CoverDto cover,
                          Float estimation) implements BookDto {
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
}
