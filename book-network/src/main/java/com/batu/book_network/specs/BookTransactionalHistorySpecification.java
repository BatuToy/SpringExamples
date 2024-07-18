package com.batu.book_network.specs;

import com.batu.book_network.entites.BookTransactionHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BookTransactionalHistorySpecification {

    public static Specification<BookTransactionHistory> withBorrowed(Long userId) {
        return new Specification<BookTransactionHistory>() {
            @Override
            public Predicate toPredicate(Root<BookTransactionHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate isOwner = criteriaBuilder.equal(root.get("user").get("id"), userId);
                return criteriaBuilder.and(isOwner);
            }
        };
    }

    public static Specification<BookTransactionHistory> isAlreadyBorrowedByUser(Long bookId, Long userId){
        return new Specification<BookTransactionHistory>() {
            @Override
        public Predicate toPredicate (Root<BookTransactionHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
                Predicate isUser = criteriaBuilder.equal(root.get("user").get("id"), userId);
                Predicate isBook = criteriaBuilder.equal(root.get("book").get("id"), bookId);
                Predicate isReturnedApproved = criteriaBuilder.isFalse(root.get("returnApproved"));
                return criteriaBuilder.and(isUser, isBook, isReturnedApproved);
            }
        };
    }

    public static Specification<BookTransactionHistory> isAlreadyBorrowedByAnotherUser(Long bookId, Long userId){
        return new Specification<BookTransactionHistory>() {
            @Override
            public Predicate toPredicate (Root<BookTransactionHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
                Predicate isUser = criteriaBuilder.notEqual(root.get("user").get("id"), userId);
                Predicate isBook = criteriaBuilder.equal(root.get("book").get("id"), bookId);
                Predicate isReturnedApproved = criteriaBuilder.isFalse(root.get("returnApproved"));
                return criteriaBuilder.and(isUser, isBook, isReturnedApproved);
            }
        };
    }

    public static Specification<BookTransactionHistory> isReturned(Long userId) {
        return new Specification<BookTransactionHistory>() {
            @Override
            public Predicate toPredicate(Root<BookTransactionHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate isReturned = criteriaBuilder.equal(root.get("book").get("owner").get("id"), userId);
                return criteriaBuilder.and(isReturned);
            }
        };
    }
}
