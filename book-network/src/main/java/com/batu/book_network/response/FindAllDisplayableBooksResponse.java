package com.batu.book_network.response;

import com.batu.book_network.common.PageResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAllDisplayableBooksResponse {
    private PageResponse<BookResponse> pageResponse;
}
