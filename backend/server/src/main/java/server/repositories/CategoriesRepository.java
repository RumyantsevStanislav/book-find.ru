package server.repositories;

import server.entities.Category;
import server.entities.dtos.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String title);

    List<CategoryDto> findAllBy();
}
