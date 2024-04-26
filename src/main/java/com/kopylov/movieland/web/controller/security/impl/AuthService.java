package com.kopylov.movieland.web.controller.security.impl;

import com.kopylov.movieland.entity.Role;
import com.kopylov.movieland.entity.User;
import com.kopylov.movieland.repository.UserRepository;
import com.kopylov.movieland.web.controller.request.AuthRequest;
import com.kopylov.movieland.web.controller.response.AuthResponse;
import com.kopylov.movieland.web.controller.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signIn(AuthRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtService.generateToken(user);
        String message = "Successfully Signed In";

        return new AuthResponse(user.getUsername(), token, message);
    }

    public AuthResponse signUp(AuthRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        User userResult = userRepository.save(user);

        if (userResult != null && userResult.getId() > 0) {
            String message = "Successfully Signed Up";
            return new AuthResponse(user.getUsername(), message);
        }
        return null;
    }
}
