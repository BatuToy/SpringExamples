package com.batu.book_network.serviceImpl;

import com.batu.book_network.entites.Token;
import com.batu.book_network.entites.User;
import com.batu.book_network.enums.TokenType;
import com.batu.book_network.repositories.TokenRepository;
import com.batu.book_network.services.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NOT_FOUND = "No such token found!";

    private final TokenRepository tokenRepository;

    public void save(Token token){
        tokenRepository.save(token);
    }

    public Token addToken(User user, String jwtToken){
        return Token
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

    public void revokeAllTokens(User user){
        tokenRepository.findAllValidTokensByUserId(user.getId()).forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            save(token);
        });
    }

    public boolean isTokenValid(String jwtToken){
        return tokenRepository.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
    }

    public Token findByToken(String jwtToken) {
        return tokenRepository.findByToken(jwtToken).orElseThrow( () -> new EntityNotFoundException(TOKEN_NOT_FOUND));
    }
}
