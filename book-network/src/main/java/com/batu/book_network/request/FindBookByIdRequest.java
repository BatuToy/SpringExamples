package com.batu.book_network.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBookByIdRequest{
    private Long bookId;
}
