package com.batu.book_network.history;

import com.batu.book_network.book.Book;
import com.batu.book_network.common.BaseEntity;
import com.batu.book_network.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class BookTransactionHistory extends BaseEntity{
    
    @ManyToOne
    @Column(name = "user_id")
    private User user;
    @ManyToOne
    @Column(name = "book_id")
    private Book book;
    
    private boolean returned;
    private boolean returnApproved;
}
