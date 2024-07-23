package com.batu.book_network.services;

import com.batu.book_network.request.ChangePasswordRequest;
import com.batu.book_network.request.LoginRequest;
import com.batu.book_network.response.AuthorizeManagerResponse;
import com.batu.book_network.response.ChangePasswordResponse;
import com.batu.book_network.response.LoginResponse;
import com.batu.book_network.request.RegistrationRequest;
import com.batu.book_network.response.RegistrationResponse;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request);
    RegistrationResponse register(RegistrationRequest request) throws MessagingException;
    AuthorizeManagerResponse authenticateManager(Authentication connectedUser);
    ChangePasswordResponse changePassword(ChangePasswordRequest request, Authentication connectedUser);
    void activateAccount(String activationCode) throws MessagingException;
}

