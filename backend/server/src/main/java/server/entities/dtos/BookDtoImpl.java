package server.entities.dtos;

import java.util.Set;

public class BookDtoImpl implements BookDto {
    private final Long isbn;
    private final String title;
    private final Set<AuthorDto> authorDtoSet;
    private final CoverDtoImpl coverDto;

    public BookDtoImpl(Long isbn, String title, Set<AuthorDto> authorDtoSet, CoverDtoImpl coverDtoImpl) {
        this.isbn = isbn;
        this.title = title;
        this.authorDtoSet = authorDtoSet;
        this.coverDto = coverDtoImpl;
    }

    @Override
    public Long getIsbn() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Set<AuthorDto> getAuthors() {
        return authorDtoSet;
    }

    @Override
    public CoverDtoImpl getCover() {
        return coverDto;
    }
}
