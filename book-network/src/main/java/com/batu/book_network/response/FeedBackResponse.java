package com.batu.book_network.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackResponse {
    private Double note;
    private String comment;
    private boolean ownFeedBack;
}
