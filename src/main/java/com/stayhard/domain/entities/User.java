package com.stayhard.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public record User(
    Long id,
    String username,
    String email,
    LocalDate createdAt,
    int level,
    int xp,
    int totalHabitsCompleted
) {
    private static final int XP_PER_LEVEL = 100;

    public User {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(email, "Email cannot be null");
        if (username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static User create(String username, String email) {
        return new User(null, username.trim(), email.toLowerCase().trim(), LocalDate.now(), 1, 0, 0);
    }

    public User withId(Long id) {
        return new User(id, username, email, createdAt, level, xp, totalHabitsCompleted);
    }

    public User addXp(int amount) {
        int newXp = xp + amount;
        int newLevel = level;
        int remainingXp = newXp;

        while (remainingXp >= XP_PER_LEVEL) {
            remainingXp -= XP_PER_LEVEL;
            newLevel++;
        }

        return new User(id, username, email, createdAt, newLevel, remainingXp, totalHabitsCompleted);
    }

    public User incrementHabitsCompleted() {
        return new User(id, username, email, createdAt, level, xp, totalHabitsCompleted + 1);
    }

    public int getXpToNextLevel() {
        return XP_PER_LEVEL - xp;
    }

    public double getLevelProgress() {
        return (double) xp / XP_PER_LEVEL;
    }
}
