package com.kopylov.movieland.web.controller.response;

import lombok.Getter;

@Getter
public class AuthResponse {

    private String token;

    private final String username;

    private final String message;

    public AuthResponse(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public AuthResponse(String username, String token, String message) {
        this.username = username;
        this.token = token;
        this.message = message;
    }
}