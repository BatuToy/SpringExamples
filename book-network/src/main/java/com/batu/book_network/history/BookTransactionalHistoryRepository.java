package com.batu.book_network.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookTransactionalHistoryRepository extends JpaRepository<BookTransactionHistory,Long>, JpaSpecificationExecutor<BookTransactionHistory> {

    @Query("""
            SELECT  history
            FROM  BookTransactionHistory  history
            WHERE history.book.id = :bookId
            AND history.user.id = :userId
            AND history.returned = false
            AND  history.returnApproved = false
           """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(@Param("bookId") Long bookId,@Param("userId") Long userId);
    @Query("""
           SELECT  history
           FROM BookTransactionHistory history
           WHERE history.book.owner.id = :userId
           AND history.book.id = :bookId
           AND history.returned = true 
           AND history.returnApproved = false
           """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(@Param("bookId") Long bookId, @Param("userId") Long userId);
}
