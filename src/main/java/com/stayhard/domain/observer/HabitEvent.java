package com.stayhard.domain.observer;

import com.stayhard.domain.entities.Habit;

import java.time.LocalDateTime;

public record HabitEvent(
    String eventType,
    Habit habit,
    LocalDateTime timestamp
) {
    public static HabitEvent created(Habit habit) {
        return new HabitEvent("CREATED", habit, LocalDateTime.now());
    }

    public static HabitEvent completed(Habit habit) {
        return new HabitEvent("COMPLETED", habit, LocalDateTime.now());
    }

    public static HabitEvent updated(Habit habit) {
        return new HabitEvent("UPDATED", habit, LocalDateTime.now());
    }

    public static HabitEvent deleted(Long habitId) {
        return new HabitEvent("DELETED", null, LocalDateTime.now());
    }
}
