package com.stayhard.domain.entities;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public record Habit(
    Long id,
    String name,
    String description,
    Priority priority,
    Status status,
    LocalDate createdAt,
    LocalDateTime completedAt,
    int streak,
    Long userId
) {
    public Habit {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(priority, "Priority cannot be null");
        Objects.requireNonNull(status, "Status cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    public static Habit create(String name, String description, Priority priority, Long userId) {
        return new Habit(
            null,
            name.trim(),
            description != null ? description.trim() : null,
            priority,
            Status.PENDING,
            LocalDate.now(),
            null,
            0,
            userId
        );
    }

    public Habit withId(Long id) {
        return new Habit(id, name, description, priority, status, createdAt, completedAt, streak, userId);
    }

    public Habit markComplete() {
        return new Habit(id, name, description, priority, Status.COMPLETED, createdAt, LocalDateTime.now(), streak + 1, userId);
    }

    public Habit markIncomplete() {
        return new Habit(id, name, description, priority, Status.PENDING, createdAt, null, 0, userId);
    }

    public Habit updateStatus(Status newStatus) {
        if (!status.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Cannot transition from " + status + " to " + newStatus);
        }
        return new Habit(id, name, description, priority, newStatus, createdAt, 
            newStatus == Status.COMPLETED ? LocalDateTime.now() : completedAt, 
            newStatus == Status.COMPLETED ? streak + 1 : streak, userId);
    }

    public Habit updatePriority(Priority newPriority) {
        return new Habit(id, name, description, newPriority, status, createdAt, completedAt, streak, userId);
    }

    public boolean isHighPriority() {
        return priority == Priority.HIGH || priority == Priority.CRITICAL;
    }

    public boolean isStreakActive() {
        return streak >= 3;
    }
}
