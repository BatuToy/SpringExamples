package com.batu.book_network.config.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotNull(message = " Full name must bu entered!")
    @NotBlank(message = " FullName must bu entered!")
    private String firstname;

    @NotNull(message = " Last name must bu entered!")
    @NotBlank (message = " Last name must bu entered!")
    private String lastname;

    @NotNull(message = " Email must bu entered!")
    @NotBlank (message = " Email must bu entered!")
    private String email;

    @NotNull(message = "Password must bu entered!")
    @NotBlank (message = "Password must bu entered!")
    @Size(min = 8, message = "Password should be at least 8 characters!")
    private String password;
}
