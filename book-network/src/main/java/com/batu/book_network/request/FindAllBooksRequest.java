package com.batu.book_network.request;

import lombok.*;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindAllBooksRequest {
    private int page;
    private int size;
    private Authentication connectedUser;
}
