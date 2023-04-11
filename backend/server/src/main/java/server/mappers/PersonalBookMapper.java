package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.entities.PersonalBook;
import server.entities.dtos.PersonalBookDto;

import java.util.List;

@Mapper(uses = {BookMapper.class})
public interface PersonalBookMapper {
    PersonalBookMapper PERSONAL_BOOK_MAPPER = Mappers.getMapper(PersonalBookMapper.class);

    @Mapping(source = "book", target = "bookDtoImpl")
    PersonalBookDto toDto(PersonalBook personalBook);

    //@Mapping(source = "user.id", target = "userId")
    List<PersonalBookDto> toDtoList(List<PersonalBook> personalBookList);

}
