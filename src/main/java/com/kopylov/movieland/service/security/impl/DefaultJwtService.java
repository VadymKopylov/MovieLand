package com.kopylov.movieland.service.security.impl;

import com.kopylov.movieland.service.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class DefaultJwtService implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Long lifetime;

    public String generateToken(UserDetails userDetails) {
        String login = userDetails.getUsername();
        return Jwts.builder()
                .subject(login)
                .issuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))
                .expiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + lifetime)))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        String login = userDetails.getUsername();
        return Jwts.builder()
                .claims(claims)
                .subject(login)
                .issuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))
                .expiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + lifetime)))
                .signWith(getSignInKey())
                .compact();
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
