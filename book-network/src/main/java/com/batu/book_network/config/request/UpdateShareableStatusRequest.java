package com.batu.book_network.config.request;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateShareableStatusRequest {
    private Long bookId;
    Authentication connectedUser;
}
