package com.batu.book_network.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private String title;
    private String authorName;
    private String isbn;
    private String synopsys;
    private String owner;
    private double rate;
    // Image of the book we ll check later!
    private byte[] cover;
    private boolean shareable;
    private boolean archived;

}
