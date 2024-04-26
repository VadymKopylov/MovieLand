package com.kopylov.movieland.web.controller.request;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;

    private String password;
}