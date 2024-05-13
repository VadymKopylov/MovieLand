package com.kopylov.movieland.web.controller.security.impl;

import com.kopylov.movieland.entity.Role;
import com.kopylov.movieland.entity.User;
import com.kopylov.movieland.repository.UserRepository;
import com.kopylov.movieland.service.impl.MyUserDetailsService;
import com.kopylov.movieland.web.controller.request.AuthRequest;
import com.kopylov.movieland.web.controller.response.AuthResponse;
import com.kopylov.movieland.web.controller.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signIn(AuthRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(signInRequest.getEmail());
        Optional<User> user  = userRepository.findByEmail(signInRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        String message = "Successfully Signed In";

        return new AuthResponse(user.get().getUsername(), token, message);
    }

    public AuthResponse signUp(AuthRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        user.setUsername(signUpRequest.getUsername());
        User userResult = userRepository.save(user);

        if (userResult != null && userResult.getId() > 0) {
            String message = "Successfully Signed Up";
            return new AuthResponse(user.getUsername(), message);
        }
        return null;
    }
}
