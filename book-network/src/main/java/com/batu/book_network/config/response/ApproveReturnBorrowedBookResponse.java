package com.batu.book_network.config.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveReturnBorrowedBookResponse {
    private Long transactionHistoryId;
}
