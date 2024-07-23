package com.batu.book_network.config.response;

import com.batu.book_network.entites.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponse {

    private String email;
    private String fullName;
    private List<Role> roles;

}
