package com.batu.book_network.config.response;

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
