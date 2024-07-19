package com.batu.book_network.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveReturnBorrowedBookResponse {
    private Long transactionHistoryId;
}
