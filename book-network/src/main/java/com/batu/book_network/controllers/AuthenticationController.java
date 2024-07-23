package com.batu.book_network.controllers;

import com.batu.book_network.config.request.ChangePasswordRequest;
import com.batu.book_network.config.request.LoginRequest;
import com.batu.book_network.config.response.AuthorizeManagerResponse;
import com.batu.book_network.config.response.ChangePasswordResponse;
import com.batu.book_network.config.response.LoginResponse;
import com.batu.book_network.config.request.RegistrationRequest;
import com.batu.book_network.config.response.RegistrationResponse;
import com.batu.book_network.services.impl.AuthenticationServiceImpl;
import com.batu.book_network.services.impl.RoleServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String DELETE_ROLE_MESSAGE = "Role has been deleted.";

    private final AuthenticationServiceImpl service;
    private final RoleServiceImpl roleServiceImpl;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException
    {
        var response = service.register(request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request){
        var response = service.authenticate(request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "activate-account")
    public void confirmAccount(@RequestParam("activationCode") String activationCode) throws MessagingException {
        service.activateAccount(activationCode);
    }

    @PatchMapping(value = "manager")
    public ResponseEntity<AuthorizeManagerResponse> authenticateManager(Authentication connectedUser){
        return ResponseEntity.ok(service.authenticateManager(connectedUser));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRole(Authentication connectedUser){
        roleServiceImpl.deleteRole(connectedUser);
        return ResponseEntity.ok(DELETE_ROLE_MESSAGE);
    }

    @PatchMapping(value = "change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.changePassword(request, connectedUser));
    }
}
