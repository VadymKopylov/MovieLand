package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.service.security.impl.AuthService;
import com.kopylov.movieland.web.controller.dto.JwtRequestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtRequestResponseDto> login(@RequestBody JwtRequestResponseDto loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtRequestResponseDto> signup(@RequestBody JwtRequestResponseDto loginRequest) {
        return ResponseEntity.ok(authService.signUp(loginRequest));
    }
}
