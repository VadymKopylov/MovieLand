package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.web.controller.request.AuthRequest;
import com.kopylov.movieland.web.controller.response.AuthResponse;
import com.kopylov.movieland.web.controller.security.impl.AuthService;
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest loginRequest) {
        return ResponseEntity.ok(authService.signUp(loginRequest));
    }
}
