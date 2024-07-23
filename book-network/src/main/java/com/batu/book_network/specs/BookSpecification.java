package com.batu.book_network.specs;

import com.batu.book_network.entites.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification(){
        throw new IllegalStateException("Utility Class!");
    }

    public static Specification<Book> withDisplayable(Long ownerId){
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate isArchived = criteriaBuilder.isFalse(root.get("archived"));
                Predicate isShareable = criteriaBuilder.isTrue(root.get("shareable"));
                Predicate isNotOwner = criteriaBuilder.notEqual(root.get("owner").get("id"), ownerId);
                return criteriaBuilder.and(isArchived, isShareable, isNotOwner);
            }
        };
    }

    public static Specification<Book> withOwnerId(Long ownerId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
