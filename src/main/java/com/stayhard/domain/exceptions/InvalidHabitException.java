package com.stayhard.domain.exceptions;

public class InvalidHabitException extends RuntimeException {
    public InvalidHabitException(String message) {
        super(message);
    }

    public static InvalidHabitException emptyName() {
        return new InvalidHabitException("Habit name cannot be empty");
    }

    public static InvalidHabitException invalidPriority(String priority) {
        return new InvalidHabitException("Invalid priority: " + priority);
    }
}
