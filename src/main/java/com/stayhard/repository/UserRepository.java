package repository;

import com.stayhard.domain.entities.User;

public interface UserRepository {
    User load();
    void save(User user);
}