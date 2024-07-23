package com.batu.book_network.security;

import java.security.Key;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Slf4j
@Service
public class JwtService {
    
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String extractUserName(String jwtToken){
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Optional<Claims> claims = extractAllClaims(jwtToken);
        if(claims.isEmpty()){
            throw new MalformedJwtException("No claim was attached!");
        }
        return claimResolver.apply(claims.get());
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(
        Map<String, Object> extraClaims, 
        UserDetails userDetails, 
        long expiration) {
        var authorities = userDetails.getAuthorities()
            .stream()
            .map((GrantedAuthority::getAuthority))
            .toList();
        return Jwts
                .builder()
                    .setClaims(extraClaims)
                    // Identifier of the claims
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .claim("authorities", authorities)
                    .signWith(getSignKey())
                    .compact();
    }

    private Optional<Claims> extractAllClaims(String jwtToken){
        log.info("Jwt: {}", jwtToken);
        try {
            log.info("Jwt parsed successfully!");
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
            return Optional.of(claims);
        }catch (MalformedJwtException e){
            log.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails){
        final String userName = extractUserName(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new java.util.Date());
    }

    private java.util.Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }       
}
