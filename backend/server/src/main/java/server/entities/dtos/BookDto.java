package server.entities.dtos;

import java.util.Set;

public interface BookDto {

    Long isbn();

    // TODO: 17.11.2022  configure annotations

    //    @Size(min = 4, message = "Title too short")
    //    @Min(value = 1, message = "Cannot be negative or zero")
    String title();

    Set<AuthorDto> authors();

    CoverDto cover();

    Float estimation();
}
