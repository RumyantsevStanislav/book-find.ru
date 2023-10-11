package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Author;
import server.entities.Series;
import server.entities.dtos.AuthorDtoImpl;
import server.entities.dtos.SeriesDtoImpl;

import java.util.List;

@Mapper
public interface SeriesMapper {
    SeriesMapper SERIES_MAPPER = Mappers.getMapper(SeriesMapper.class);

    SeriesDtoImpl toDto(Series series);

}
