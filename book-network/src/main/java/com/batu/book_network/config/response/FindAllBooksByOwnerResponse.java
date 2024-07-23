package com.batu.book_network.config.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBooksByOwnerResponse {
    private PageResponse<BookResponse> pageResponse;
}
