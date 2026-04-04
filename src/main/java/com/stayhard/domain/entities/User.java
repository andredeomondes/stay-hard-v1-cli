package com.stayhard.domain.entities;

public record User(
        String name,
        int daysCompleted,
        int daysFailed,
        int currentStreak,
        int maxStreak
) {

    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }

    public User(String name) {
        this(name, 0, 0, 0, 0);
    }

    public User withName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        return new User(newName, daysCompleted, daysFailed, currentStreak, maxStreak);
    }

    public User addCompletedDay() {
        int newStreak = currentStreak + 1;
        int newMaxStreak = Math.max(maxStreak, newStreak);
        return new User(
                name,
                daysCompleted + 1,
                daysFailed,
                newStreak,
                newMaxStreak
        );
    }

    public User addFailedDay() {
        return new User(
                name,
                daysCompleted,
                daysFailed + 1,
                0,
                maxStreak
        );
    }
}
