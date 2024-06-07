package com.kopylov.movieland.web.controller.security.impl;

import com.kopylov.movieland.web.controller.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class DefaultJwtService implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private int lifetime;

    public String generateToken(UserDetails userDetails) {
        String login = userDetails.getUsername();
        return Jwts.builder()
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifetime))
                .signWith(getSignInKey())
                .compact();
    }

    public String getTokenFromHeader(String header) {
        return header.substring(AUTH_SCHEME.length());
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
