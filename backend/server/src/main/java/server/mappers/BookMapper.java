package server.mappers;

// import backend.entities.dtos.BookDto; д.б. класс, а не интерфейс
import org.mapstruct.Mapper;

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
