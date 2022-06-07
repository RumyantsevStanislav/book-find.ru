package server.services;

import server.entities.Category;
import server.entities.dtos.CategoryDto;
import server.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    private CategoriesRepository categoriesRepository;

    @Autowired
    public void setCategoriesRepository(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Category findByTitle(String title) {
        return categoriesRepository.findByTitle(title);
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoriesRepository.findAllById(ids);
    }

    public  List<CategoryDto> getDtoData(){
        return categoriesRepository.findAllBy();
    }
}
