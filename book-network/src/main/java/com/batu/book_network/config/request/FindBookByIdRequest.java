package com.batu.book_network.config.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBookByIdRequest{
    private Long bookId;
}
