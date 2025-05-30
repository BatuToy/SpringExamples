package com.batu.book_network.services;

import com.batu.book_network.config.request.ChangePasswordRequest;
import com.batu.book_network.config.request.LoginRequest;
import com.batu.book_network.config.response.AuthorizeManagerResponse;
import com.batu.book_network.config.response.ChangePasswordResponse;
import com.batu.book_network.config.response.LoginResponse;
import com.batu.book_network.config.request.RegistrationRequest;
import com.batu.book_network.config.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request);
    RegistrationResponse register(@Valid  RegistrationRequest request) throws MessagingException;
    AuthorizeManagerResponse authenticateManager(Authentication connectedUser);
    ChangePasswordResponse changePassword(ChangePasswordRequest request, Authentication connectedUser);
    void activateAccount(String activationCode) throws MessagingException;
}

