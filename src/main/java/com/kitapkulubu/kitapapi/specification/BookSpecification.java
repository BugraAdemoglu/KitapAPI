package com.kitapkulubu.kitapapi.specification;

import com.kitapkulubu.kitapapi.model.Book;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class BookSpecification {

    public static Specification<Book> hasAuthor(Long authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("author").get("id"), authorId);
        };
    }

    public static Specification<Book> hasPublishHouse(Long publishHouseId) {
        return (root, query, criteriaBuilder) -> {
            if (publishHouseId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("publishHouse").get("id"), publishHouseId);
        };
    }
}