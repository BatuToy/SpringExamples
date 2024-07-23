package com.batu.book_network.response;

import com.batu.book_network.entites.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizeManagerResponse {
    private String fullName;
    private List<Role> roles;
}
