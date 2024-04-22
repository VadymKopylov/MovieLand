package com.kopylov.movieland.service.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}