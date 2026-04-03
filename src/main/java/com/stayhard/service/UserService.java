package com.stayhard.service;

import com.stayhard.domain.entities.User;
import com.stayhard.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final User user;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.user = userRepository.load();
    }

    public User getUser() {
        return user;
    }

    public void registerCompletedDay() {
        user.addCompletedDay();
        userRepository.save(user);
    }

    public void registerFailedDay() {
        user.addFailedDay();
        userRepository.save(user);
    }
}