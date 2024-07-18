package com.batu.book_network.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.batu.book_network.request.LoginRequest;
import com.batu.book_network.response.LoginResponse;
import com.batu.book_network.request.RegistrationRequest;
import com.batu.book_network.response.RegistrationResponse;
import com.batu.book_network.convert.Mapper;
import com.batu.book_network.entites.Token;
import com.batu.book_network.entites.User;
import com.batu.book_network.role.Roles;
import com.batu.book_network.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.batu.book_network.email.EmailTemplateName;

import jakarta.mail.MessagingException;

@Service
public class AuthenticationService {

    // declare a const variable for userRole enum


    private final TokenService tokenService;
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final Mapper mapper;
    private final AuthenticationManager authenticationManager;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public AuthenticationService(UserService userService,
                                 TokenService tokenService, RoleService roleService,
                                 EmailService emailService, JwtService jwtService,
                                 Mapper mapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse authenticate(LoginRequest request){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        var jwt = jwtService.generateToken(claims, user);
        return LoginResponse
                    .builder().token(jwt).build();
    }

    public RegistrationResponse register (RegistrationRequest request) throws MessagingException{
        exitsByEmail(request.getEmail());
        var user = mapper.registerToUser(request);
        userService.saveUser(user);
        roleService.addRole(user, Roles.ROLE_USER);
        sendValidationEmail(user);
        return mapper.userToResponse(user);

    }

    private void sendValidationEmail(User user) throws MessagingException{
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
            user.getEmail(),
            user.getFullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl, 
            newToken,
            "Account activation message!");
    }


    // Generate token Randomly for generating a activationToken that will be used for activation process.


    //@Transactional
    /** FIX ISSUE
      In any exception, rollback the transaction.Cause the token save operation is failed.
     **/
    public void activateAccount(String activationCode) throws MessagingException {
        var savedToken = tokenService.findByToken(activationCode);
        isTokenExpiredSendEmail(savedToken);
        var user = savedToken.getUser();
        user.setEnable(true);
        userService.saveUser(user);
        savedToken.setCreatedAt(LocalDateTime.now());
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenService.saveToken(savedToken);
    }


    private String generateAndSaveActivationToken(User user) {
        int length = 6;
        var generatedToken = generateActivationCode(length);
        var token = Token
            .builder()
            .token(generatedToken)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(30))
            .user(user)
            .build();
        tokenService.saveToken(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    private void isTokenExpiredSendEmail(Token token) throws MessagingException {
        var savedToken = tokenService.findByToken(token.getToken());
        if(LocalDateTime.now().isAfter(token.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new IllegalArgumentException("Token expired!");
        }
    }

    private void exitsByEmail(String email){
        userService.existByEmail(email);
    }
}
