package com.batu.book_network.config.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllBooksResponse {
    private PageResponse<BookResponse> pageResponse;
}
