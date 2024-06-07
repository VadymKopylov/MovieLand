package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.User;
import com.kopylov.movieland.repository.UserRepository;
import com.kopylov.movieland.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
