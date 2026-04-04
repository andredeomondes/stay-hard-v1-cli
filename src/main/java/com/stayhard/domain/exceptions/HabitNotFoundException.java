package com.stayhard.domain.exceptions;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(Long id) {
        super("Habit not found with id: " + id);
    }

    public HabitNotFoundException(String message) {
        super(message);
    }
}
