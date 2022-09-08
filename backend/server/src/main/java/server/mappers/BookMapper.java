package server.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.entities.Book;
import server.entities.dtos.BookDtoImpl;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, CoverMapper.class})
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "book.authors", target = "authorDtoSet")
    @Mapping(source = "book.cover", target = "coverDtoImpl")
    BookDtoImpl toDto(Book book);

    //    @Mapping(source = "categoryDto", target = "category")
    //    Book toBook(BookDto bookDto);
    //
    //    @InheritInverseConfiguration
    //    BookDto fromBook(Book book);
    //
    //    List<Book> toBookList(List<BookDto> bookDtos);
    //
}
