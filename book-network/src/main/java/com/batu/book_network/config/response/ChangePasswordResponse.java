package com.batu.book_network.config.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordResponse {
    private String password;
}
