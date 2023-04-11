package server.entities.dtos;

import java.util.Set;

public record BookDtoImpl(Long isbn, String title, Set<AuthorDto> authors, CoverDto cover,
                          Float estimation) implements BookDto {
}
