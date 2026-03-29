package repository;

import domain.entities.User;

public interface UserRepository {
    User load();
    void save(User user);
}