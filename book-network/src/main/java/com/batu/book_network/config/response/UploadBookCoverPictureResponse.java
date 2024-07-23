package com.batu.book_network.config.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadBookCoverPictureResponse {
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

