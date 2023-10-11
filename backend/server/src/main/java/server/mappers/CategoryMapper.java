package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.Category;
import server.entities.dtos.CategoryDto;
import server.entities.dtos.CategoryDtoImpl;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDto categoryDto);

    CategoryDtoImpl fromCategory(Category category);

    List<Category> toCategoryList(List<CategoryDto> categories);

    List<CategoryDtoImpl> fromCategoryList(List<Category> categories);
}
