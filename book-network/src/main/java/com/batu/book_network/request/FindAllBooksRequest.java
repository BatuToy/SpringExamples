package com.batu.book_network.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAllBooksRequest {
    private int page;
    private int size;
}