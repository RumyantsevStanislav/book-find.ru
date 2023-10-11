package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Author;
import server.entities.Publisher;
import server.entities.dtos.AuthorDtoImpl;
import server.entities.dtos.PublisherDtoImpl;

import java.util.List;

@Mapper
public interface PublisherMapper {
    PublisherMapper PUBLISHER_MAPPER = Mappers.getMapper(PublisherMapper.class);

    PublisherDtoImpl toDto(Publisher publisher);
}
