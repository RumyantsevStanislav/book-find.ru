package server.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import server.entities.Book;
import server.entities.Category;
import server.repositories.specifications.BookSpecifications;

import java.util.List;
import java.util.Map;

// TODO: 24.12.2022 optimize specificationBuilder: https://habr.com/ru/company/rshb/blog/521220/
@Getter
public class BookFilter {
    private Specification<Book> spec;
    private final StringBuilder filterDefinition;

    public BookFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(BookSpecifications.priceGreaterOrEqualsThen(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(BookSpecifications.priceLesserOrEqualsThen(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if (map.containsKey("title") && !map.get("title").isEmpty()) {
            // TODO: 24.12.2022 think about min lengths of title 
            String title = map.get("title");
            spec = spec.and(BookSpecifications.titleLike(title));
            filterDefinition.append("&title=").append(title);
        }
        if (map.containsKey("min_estimation") && !map.get("min_estimation").isEmpty()) {
            int minEstimation = Integer.parseInt(map.get("min_estimation"));
            spec = spec.and(BookSpecifications.estimationGreaterOrEqualsThen(minEstimation));
            filterDefinition.append("&min_estimation=").append(minEstimation);
        }
        if (map.containsKey("max_estimation") && !map.get("max_estimation").isEmpty()) {
            int maxEstimation = Integer.parseInt(map.get("max_estimation"));
            spec = spec.and(BookSpecifications.estimationLesserOrEqualsThen(maxEstimation));
            filterDefinition.append("&max_estimation=").append(maxEstimation);
        }
        if (map.containsKey("min_year") && !map.get("min_year").isEmpty()) {
            int minYear = Integer.parseInt(map.get("min_year"));
            spec = spec.and(BookSpecifications.yearGreaterOrEqualsThen(minYear));
            filterDefinition.append("&min_year=").append(minYear);
        }
        if (map.containsKey("max_year") && !map.get("max_year").isEmpty()) {
            int maxYear = Integer.parseInt(map.get("max_year"));
            spec = spec.and(BookSpecifications.yearLesserOrEqualsThen(maxYear));
            filterDefinition.append("&max_year=").append(maxYear);
        }
        if (categories != null && !categories.isEmpty()) {
            Specification<Book> specCategories = null;
            for (Category c : categories) {
                if (specCategories == null) {
                    specCategories = BookSpecifications.categoryIs(c);
                } else {
                    specCategories = specCategories.or(BookSpecifications.categoryIs(c));
                }
            }
            spec = spec.and(specCategories);
        }
    }
}
