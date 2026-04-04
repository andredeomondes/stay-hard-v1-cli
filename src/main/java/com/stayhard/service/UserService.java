package com.stayhard.service;

import com.stayhard.domain.entities.User;
import com.stayhard.domain.exceptions.UserNotFoundException;
import com.stayhard.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository repository;
    private User currentUser;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(String username, String email) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.create(username, email);
        return repository.save(user);
    }

    public User findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User update(User user) {
        return repository.update(user);
    }

    public void delete(Long id) {
        findById(id);
        repository.delete(id);
    }

    public User login(String username) {
        User user = repository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
        this.currentUser = user;
        return user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User addXp(int amount) {
        if (currentUser == null) {
            throw new IllegalStateException("No user logged in");
        }
        User updated = currentUser.addXp(amount);
        currentUser = repository.update(updated);
        return currentUser;
    }

    public User completeHabit() {
        if (currentUser == null) {
            throw new IllegalStateException("No user logged in");
        }
        User updated = currentUser.incrementHabitsCompleted().addXp(10);
        currentUser = repository.update(updated);
        return currentUser;
    }
}
