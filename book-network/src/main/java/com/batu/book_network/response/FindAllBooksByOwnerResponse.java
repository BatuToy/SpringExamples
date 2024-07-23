package com.batu.book_network.response;

import com.batu.book_network.common.PageResponse;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBooksByOwnerResponse {
    private PageResponse<BookResponse> pageResponse;
}
