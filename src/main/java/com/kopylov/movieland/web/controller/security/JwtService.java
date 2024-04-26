package com.kopylov.movieland.web.controller.security;

import com.kopylov.movieland.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {

    String AUTH_SCHEME = "Bearer ";

    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}