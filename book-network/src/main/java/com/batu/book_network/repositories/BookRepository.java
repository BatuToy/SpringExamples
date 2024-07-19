package com.batu.book_network.repositories;

import com.batu.book_network.entites.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    /** FIXED BY SPECS**/
    @Query("""
           SELECT book
           FROM Book book
           WHERE book.archived = false
           AND book.sharable = true
           AND book.id != :userId
           """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Long userId);
}
