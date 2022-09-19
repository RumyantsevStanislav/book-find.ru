package server.repositories.specifications;

import server.entities.Category;
import org.springframework.data.jpa.domain.Specification;
import server.entities.Book;

public class BookSpecifications {
    public static Specification<Book> priceGreaterOrEqualsThen(int minPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Book> priceLesserOrEqualsThen(int maxPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Book> titleLike(String title) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Book> categoryIs(Category category) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> {

            //            Join join = root.join("categories");
            //            return criteriaBuilder.equal(join.get("id"), category.getId());
            return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
