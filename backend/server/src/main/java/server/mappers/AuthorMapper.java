package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Author;
import server.entities.dtos.AuthorDtoImpl;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper AUTHOR_MAPPER = Mappers.getMapper(AuthorMapper.class);

    AuthorDtoImpl toDtoList(Author author);

    List<AuthorDtoImpl> toDto(List<Author> authors);
}
