package com.kopylov.movieland.web.controller.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String AUTH_SCHEME = "Bearer ";

    String generateToken(UserDetails userDetails);

    String getTokenFromHeader(String header);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}