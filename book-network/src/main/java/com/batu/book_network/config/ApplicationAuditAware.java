package com.batu.book_network.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.batu.book_network.entites.User;
public class ApplicationAuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        // we can reach our authentication prop with using SecurityContextHolder which we can used the methods by order like getContext and getAuthentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        User userPrinciples = (User) authentication.getPrincipal();
        // ofNullable means that if the current field we pulled is null it 
        // returns a optional type of null if it not then it returns the field we provide.
        return Optional.ofNullable(userPrinciples.getId());
    }
}
