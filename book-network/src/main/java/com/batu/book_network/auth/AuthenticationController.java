package com.batu.book_network.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException
    {
        var response = service.register(request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request){
        var response = service.authenticate(request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/activate-account")
    public void confirmAccount(@RequestParam String activationCode) throws MessagingException {
        service.activateAccount(activationCode);
    }
}