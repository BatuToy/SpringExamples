package com.batu.book_network.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBookByIdResponse {
    private String title;
    private String authorName;
    private String isbn;
    private String synopsys;
    private String owner;
    private double rate;
    private byte[] cover;
    private boolean shareable;
    private boolean archived;
}
