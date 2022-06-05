package server.entities.dtos;

import server.entities.Author;
import server.entities.Cover;

import java.util.Set;

public interface BookDto {

//    @Size(min = 4, message = "Title too short")
//    @Min(value = 1, message = "Cannot be negative or zero")
    String getTitle();

    Set<AuthorDto> getAuthors();

    CoverDto getCover();
}
