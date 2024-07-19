package com.batu.book_network.response;

import com.batu.book_network.common.PageResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllReturnedBooksResponse {
    private PageResponse<BorrowedBookResponse> pageResponse;
}
