package com.batu.book_network.config.request;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookRequest {
    private Long bookId;
    private Authentication connectedUser;
}
