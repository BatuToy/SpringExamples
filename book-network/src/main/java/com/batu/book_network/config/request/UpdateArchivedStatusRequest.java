package com.batu.book_network.config.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateArchivedStatusRequest {
    private Long bookId;
    private Authentication connectedUser;
}
