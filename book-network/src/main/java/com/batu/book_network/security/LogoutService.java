package com.batu.book_network.security;

import com.batu.book_network.impl.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenServiceImpl tokenServiceImpl;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt = authHeader.substring(7);
        var token = tokenServiceImpl.findByToken(jwt);
        if(token != null){
            token.setExpired(true);
            token.setRevoked(true);
            tokenServiceImpl.save(token);
            // deletes the token from user.
            SecurityContextHolder.clearContext();
        }
    }
}
