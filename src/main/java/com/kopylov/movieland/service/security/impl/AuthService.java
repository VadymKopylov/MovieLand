package com.kopylov.movieland.service.security.impl;

import com.kopylov.movieland.entity.Role;
import com.kopylov.movieland.entity.User;
import com.kopylov.movieland.repository.UserRepository;
import com.kopylov.movieland.service.security.JwtService;
import com.kopylov.movieland.web.controller.dto.JwtRequestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtRequestResponseDto signIn(JwtRequestResponseDto signInRequest) {
        JwtRequestResponseDto resp = new JwtRequestResponseDto();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            resp.setStatusCode(200);
            resp.setToken(token);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("2h");
            resp.setMessage("Successfully Signed In");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public JwtRequestResponseDto signUp(JwtRequestResponseDto signUpRequest) {
        JwtRequestResponseDto resp = new JwtRequestResponseDto();
        try {
            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(Role.USER);
            User userResult = userRepository.save(user);
            if (userResult != null && userResult.getId() > 0) {
                resp.setUser(userResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
}
