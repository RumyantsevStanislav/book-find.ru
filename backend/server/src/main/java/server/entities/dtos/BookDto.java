package server.entities.dtos;

import java.util.Set;

public interface BookDto {

    Long getIsbn();

    // TODO: 17.11.2022  configure annotations

    //    @Size(min = 4, message = "Title too short")
    //    @Min(value = 1, message = "Cannot be negative or zero")
    String getTitle();

    Set<AuthorDto> getAuthors();

    CoverDto getCover();

    Float getEstimation();
}
