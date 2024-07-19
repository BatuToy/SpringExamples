package com.batu.book_network.response;

import com.batu.book_network.common.PageResponse;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class FindAllBooksByOwnerResponse {
    private PageResponse<BookResponse> pageResponse;
}
