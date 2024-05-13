package com.kopylov.movieland.web.controller.request;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;

    private String email;

    private String password;
}