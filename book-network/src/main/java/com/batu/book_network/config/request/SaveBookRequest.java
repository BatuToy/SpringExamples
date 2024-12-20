package com.batu.book_network.config.request;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveBookRequest {
    private BookRequest bookRequest;
    Authentication connectedUser;
}
