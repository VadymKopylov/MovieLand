package com.kopylov.movieland.web.controller.dto;

import com.kopylov.movieland.entity.User;
import lombok.Data;

@Data
public class JwtRequestResponseDto {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private String email;
    private String role;
    private String password;
    private User user;
}