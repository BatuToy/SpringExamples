package com.batu.book_network.config.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAllDisplayableBooksResponse {
    private PageResponse<BookResponse> pageResponse;
}
