package com.batu.book_network.services;

import com.batu.book_network.entites.Token;
import com.batu.book_network.entites.User;

public interface TokenService {
    void save(Token token);
    Token addToken(User user, String jwtToken);
    void revokeAllTokens(User user);
    boolean isTokenValid(String jwtToken);
    Token findByToken(String jwtToken);
}
