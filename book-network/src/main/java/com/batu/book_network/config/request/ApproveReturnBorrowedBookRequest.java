package com.batu.book_network.config.request;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveReturnBorrowedBookRequest {
    private Authentication connectedUser;
    private Long bookId;
}