package com.batu.book_network.serviceImpl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import com.batu.book_network.exception.OperationNotPermittedException;
import com.batu.book_network.request.ChangePasswordRequest;
import com.batu.book_network.request.LoginRequest;
import com.batu.book_network.response.AuthorizeManagerResponse;
import com.batu.book_network.response.ChangePasswordResponse;
import com.batu.book_network.response.LoginResponse;
import com.batu.book_network.request.RegistrationRequest;
import com.batu.book_network.response.RegistrationResponse;
import com.batu.book_network.convert.Mapper;
import com.batu.book_network.entites.Activation;
import com.batu.book_network.entites.User;
import com.batu.book_network.enums.Roles;
import com.batu.book_network.security.JwtService;
import com.batu.book_network.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.batu.book_network.email.EmailTemplateName;

import jakarta.mail.MessagingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    // declare a const variable for userRole enum


    private final ActivationService activationService;
    private final TokenService tokenService;
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final Mapper mapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public LoginResponse authenticate(LoginRequest request){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassWord()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        tokenService.revokeAllTokens(user);
        var jwt = jwtService.generateToken(claims, user);
        tokenService.save(tokenService.addToken(user, jwt));
        return LoginResponse
                    .builder().token(jwt).build();
    }

    public RegistrationResponse register (RegistrationRequest request) throws MessagingException{
        // check the mail.
        exitsByEmail(request.getEmail());
        var user = mapper.registerToUser(request);
        roleService.addRole(userService.save(user), Roles.ROLE_USER);
        // check the role.
        existByRole(user);
        sendValidationEmail(user);
        return mapper.userToResponse(user);
    }

    public AuthorizeManagerResponse authenticateManager (Authentication connectedUser){
        User user = (User)connectedUser.getPrincipal();
        roleService.addRole(user, Roles.ROLE_ADMIN);
        log.info("{}", user.getAuthorities());
        return mapper.toAuthorizeManagerResponse(user);
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        checkPassword(request, user);
        user.setPassWord(encoder.encode(request.getNewPassword()));
        userService.save(user);
        return mapper.toChangePasswordResponse(user);
    }

    //@Transactional
    /** FIX ISSUE
      In any exception, rollback the transaction.Cause the token save operation is failed.
     **/
    public void activateAccount(String activationCode) throws MessagingException {
        var activation = activationService.findByActivationCode(activationCode);
        isTokenExpiredSendEmail(activation);
        var user = activation.getUser();
        user.setEnable(true);
        userService.save(user);
        activation.setCreatedAt(LocalDateTime.now());
        activation.setValidatedAt(LocalDateTime.now());
        activationService.save(activation);
    }

    private void sendValidationEmail(User user) throws MessagingException{
        var newActivationToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
            user.getEmail(),
            user.getFullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl, 
            newActivationToken,
            "Account activation message!");
    }

    private String generateAndSaveActivationToken(User user) {
        int length = 6;
        var generatedCode = generateActivationCode(length);
        var activationCode = Activation
            .builder()
            .activationCode(generatedCode)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(5))
            .user(user)
            .build();
        activationService.save(activationCode);
        return generatedCode;
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

    private void isTokenExpiredSendEmail(Activation activation) throws MessagingException {
        if(LocalDateTime.now().isAfter(activation.getExpiresAt())){
            sendValidationEmail(activation.getUser());
            throw new IllegalArgumentException("Activation expired!");
        }
    }

    private void exitsByEmail(String email){
        userService.existByEmail(email);
    }
    private void existByRole(User user){
        roleService.existByRole(user);
    }

    private void checkPassword(ChangePasswordRequest request, User user) {
        if(!encoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new OperationNotPermittedException("Password is wrong!");
        }
        if(!Objects.equals(request.getNewPassword(), request.getConfirmPassword())){
            throw new OperationNotPermittedException("Passwords are not equal!");
        }
    }
}
