package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Cover;
import server.entities.dtos.CoverDtoImpl;

@Mapper
public interface CoverMapper {
    CoverMapper COVER_MAPPER = Mappers.getMapper(CoverMapper.class);

    CoverDtoImpl toDto(Cover cover);
}
