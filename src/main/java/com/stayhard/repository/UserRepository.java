package com.stayhard.repository;

import com.stayhard.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User update(User user);
    void delete(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
