package server.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import server.entities.Author;
import server.entities.Book;
import server.entities.Category;

import javax.persistence.criteria.Join;

// TODO: 24.12.2022 optimize methods -> root.get("parameter")
public class BookSpecifications {
    public static Specification<Book> priceGreaterOrEqualsThen(int minPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Book> priceLesserOrEqualsThen(int maxPrice) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    // TODO: 24.12.2022 figure out with criteriaQuery
    public static Specification<Book> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), String.format("%%%s%%", title).toUpperCase());
    }

    public static Specification<Book> authorNameLike(String authorName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Author, Book> booksAuthor = root.join("authors");
            criteriaQuery.distinct(true);
            return criteriaBuilder.like(criteriaBuilder.upper(booksAuthor.get("name")), String.format("%%%s%%", authorName).toUpperCase());
        };
    }

    public static Specification<Book> estimationGreaterOrEqualsThen(int minEstimation) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("estimation"), minEstimation);
    }

    public static Specification<Book> estimationLesserOrEqualsThen(int maxEstimation) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("estimation"), maxEstimation);
    }

    public static Specification<Book> yearGreaterOrEqualsThen(int minYear) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear);
    }

    public static Specification<Book> yearLesserOrEqualsThen(int maxYear) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear);
    }

    public static Specification<Book> categoryIs(Category category) {
        return (Specification<Book>) (root, criteriaQuery, criteriaBuilder) -> {
            // TODO: 17.11.2022 figure out

            //            Join join = root.join("categories");
            //            return criteriaBuilder.equal(join.get("id"), category.getId());
            return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
