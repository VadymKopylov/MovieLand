package com.kopylov.movieland.service;

import com.kopylov.movieland.entity.User;

public interface UserService {

    User findByEmail(String email);
}
