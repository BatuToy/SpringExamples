package com.batu.book_network.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }

    public Token findByToken(String activationCode){
        return tokenRepository.findByToken(activationCode).orElseThrow(() -> new IllegalStateException("Token not initialized!"));
    }

    public void deleteToken(Token token){
        tokenRepository.delete(token);
    }

    public void deleteTokenById(Long id){
        tokenRepository.deleteById(id);
    }
}
