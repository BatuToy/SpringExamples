package com.batu.book_network.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    @NotNull
    @NotBlank
    private String currentPassword;
    @NotNull
    @NotBlank
    private String newPassword;
    @NotNull
    @NotBlank
    private String confirmPassword;
}
