package backend.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import backend.entities.Book;
import backend.repositories.specifications.BookSpecifications;

import java.util.Map;

@Getter
public class BookFilter {
    private Specification<Book> spec;
    private StringBuilder filterDefinition;

    public BookFilter (Map<String, String> map){
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if(map.containsKey("min_price") && !map.get("min_price").isEmpty()){
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(BookSpecifications.priceGreaterOrEqualsThen(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if(map.containsKey("max_price") && !map.get("max_price").isEmpty()){
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(BookSpecifications.priceGreaterOrEqualsThen(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
    }
}
