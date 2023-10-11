package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Author;
import server.entities.Genre;
import server.entities.dtos.AuthorDtoImpl;
import server.entities.dtos.GenreDtoImpl;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper GENRE_MAPPER = Mappers.getMapper(GenreMapper.class);

    GenreDtoImpl toDto(Genre genre);
}
