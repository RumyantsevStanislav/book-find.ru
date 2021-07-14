package backend.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import backend.entities.Book;

public class BookSpecifications {
    public static Specification<Book> priceGreaterOrEqualsThen(int minPrice){
        return  (Specification<Book>) (root,criteriaQuery, criteriaBuilder) ->criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
    public static Specification<Book> priceLesserOrEqualsThen(int maxPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
