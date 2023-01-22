package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Author;
import server.entities.dtos.AuthorDtoImpl;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper AUTHOR_MAPPER = Mappers.getMapper(AuthorMapper.class);

    AuthorDtoImpl toDto(Author author);

    List<AuthorDtoImpl> toDtoList(List<Author> authors);
}
