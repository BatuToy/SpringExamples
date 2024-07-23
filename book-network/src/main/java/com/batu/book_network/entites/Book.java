package com.batu.book_network.entites;

import  java.util.List;

import com.batu.book_network.entites.enums.BookStatus;
import com.batu.book_network.utils.FileUtils;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {
    private String title;
    private String synopsys;
    private String isbn;
    private boolean shareable;
    private boolean archived;
    private String authorName;
    private String bookCover;
    private BookStatus status;

    // In many to one relation don't map the object that is annotate by one. We can join a column by name prop which is : name:""
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    // TODO : BUNLARI SERVIS KATMANINA CEK - business logic işler orada olmalı !
    public double getRate(){
        if(feedbacks == null || feedbacks.isEmpty()){
            return 0.0;
        }
        var rate = this.feedbacks
                .stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        return Math.round(rate * 10.0) / 10.0;

    }

    public byte[] getBookCoverPic() {
        return FileUtils.readFileFromLocation(getBookCover());
    }
}
