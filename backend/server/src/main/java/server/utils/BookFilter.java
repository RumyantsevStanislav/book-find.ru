package server.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import server.entities.Book;
import server.entities.Category;
import server.repositories.specifications.BookSpecifications;

import java.util.List;
import java.util.Map;

@Getter
public class BookFilter {
    private Specification<Book> spec;
    private StringBuilder filterDefinition;

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
            String title = map.get("title");
            spec = spec.and(BookSpecifications.titleLike(title));
            filterDefinition.append("&title=").append(title);
        }
        if (categories != null && !categories.isEmpty()) {
            Specification specCategories = null;
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
