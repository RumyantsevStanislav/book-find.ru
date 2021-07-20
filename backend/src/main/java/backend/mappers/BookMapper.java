package backend.mappers;

import backend.entities.Book;
// import backend.entities.dtos.BookDto; д.б. класс, а не интерфейс
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CategoryMapper.class})
public interface BookMapper {
//    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);
//
//    @Mapping(source = "categoryDto", target = "category")
//    Book toBook(BookDto bookDto);
//
//    @InheritInverseConfiguration
//    BookDto fromBook(Book book);
//
//    List<Book> toBookList(List<BookDto> bookDtos);
//
//    List<BookDto> fromBookList(List<Book> books);
}
