package backend.repositories;

import backend.entities.Category;
import backend.entities.dtos.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    List<CategoryDto> findAllBy();
}
