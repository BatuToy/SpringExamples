package com.batu.book_network.config.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBorrowedBooksResponse {
    private PageResponse<BorrowedBookResponse> pageResponse;
}
