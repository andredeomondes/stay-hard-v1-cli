package com.stayhard.domain.observer;

import com.stayhard.domain.entities.Habit;

import java.time.LocalDateTime;

public record HabitEvent(
    String type,
    Habit habit,
    LocalDateTime timestamp
) {
    public static final String COMPLETED = "COMPLETED";
    public static final String STARTED = "STARTED";
    public static final String DELETED = "DELETED";
    public static final String ADDED = "ADDED";
    public static final String DAY_FINISHED = "DAY_FINISHED";
}
