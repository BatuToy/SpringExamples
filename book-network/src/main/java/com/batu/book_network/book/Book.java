package com.batu.book_network.book;

import java.util.List;

import com.batu.book_network.common.BaseEntity;
import com.batu.book_network.feedback.Feedback;
import com.batu.book_network.file.FileUtils;
import com.batu.book_network.history.BookTransactionHistory;
import com.batu.book_network.user.User;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity{
    private String title;
    private String synopsys;
    private String isbn;
    private boolean sharable;
    private boolean archived;
    private String authorName;
    private String bookCover;

    // In many to one relation don't map the object that is annotate by one. We can join a column by name prop which is : name:""
    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    // @Transient is written for this field should not be persist in to the db.
    @Transient
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

    @Transient
    public byte[] getBookCoverPic() {
        return FileUtils.readFileFromLocation(getBookCover());
    }
}
