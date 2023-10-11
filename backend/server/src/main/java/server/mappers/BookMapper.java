package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import server.entities.Book;
import server.entities.dtos.BookDtoFullImpl;
import server.entities.dtos.BookDtoImpl;

@Mapper(uses = {AuthorMapper.class, CoverMapper.class, CategoryMapper.class, PublisherMapper.class, GenreMapper.class,
        SeriesMapper.class, BookReviewsMapper.class})
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "authors", target = "authors")
    @Mapping(source = "cover", target = "cover")
    BookDtoImpl toDto(Book book);

    @Mapping(source = "authors", target = "authors")
    @Mapping(source = "cover", target = "cover")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "publisher", target = "publisher")
    @Mapping(source = "genre", target = "genre")
    @Mapping(source = "series", target = "series")
    @Mapping(source = "reviews", target = "reviews")
    BookDtoFullImpl toFullDto(Book book);
    // TODO: 17.11.2022  figure out

    //    @Mapping(source = "categoryDto", target = "category")
    //    Book toBook(BookDto bookDto);
    //
    //    @InheritInverseConfiguration
    //    BookDto fromBook(Book book);
    //
    //    List<Book> toBookList(List<BookDto> bookDtos);
    //
}
