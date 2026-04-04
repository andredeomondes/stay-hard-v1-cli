package com.stayhard.domain.entities;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public record Habit(
    Long id,
    String name,
    String description,
    Priority priority,
    Status status,
    LocalDate createdAt,
    LocalDateTime completedAt,
    LocalDateTime deadline,
    int streak,
    LocalDate lastCompletedDate,
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
        LocalDateTime now = LocalDateTime.now();
        return new Habit(
            null,
            name.trim(),
            description != null ? description.trim() : null,
            priority,
            Status.PENDING,
            LocalDate.now(),
            null,
            now.plusHours(24),
            0,
            null,
            userId
        );
    }

    public Habit withId(Long id) {
        return new Habit(id, name, description, priority, status, createdAt, completedAt, deadline, streak, lastCompletedDate, userId);
    }

    public Habit markComplete() {
        return new Habit(
            id, name, description, priority, Status.COMPLETED, createdAt, 
            LocalDateTime.now(), deadline, streak, LocalDate.now(), userId
        );
    }

    public Habit renewForNewDay() {
        LocalDateTime now = LocalDateTime.now();
        int newStreak = streak;
        
        if (status == Status.COMPLETED && lastCompletedDate != null) {
            newStreak = streak + 1;
        }
        
        return new Habit(
            id, name, description, priority, Status.PENDING, createdAt,
            null, now.plusHours(24), newStreak, null, userId
        );
    }

    public Habit markIncomplete() {
        return new Habit(
            id, name, description, priority, Status.PENDING, createdAt,
            null, deadline, 0, null, userId
        );
    }

    public Habit renewWithoutStreakIncrease() {
        LocalDateTime now = LocalDateTime.now();
        return new Habit(
            id, name, description, priority, Status.PENDING, createdAt,
            null, now.plusHours(24), 0, null, userId
        );
    }

    public Habit updateStatus(Status newStatus) {
        if (!status.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Cannot transition from " + status + " to " + newStatus);
        }
        return new Habit(id, name, description, priority, newStatus, createdAt, 
            newStatus == Status.COMPLETED ? LocalDateTime.now() : completedAt, deadline,
            newStatus == Status.COMPLETED ? streak : streak, 
            newStatus == Status.COMPLETED ? LocalDate.now() : lastCompletedDate, userId);
    }

    public Habit updatePriority(Priority newPriority) {
        return new Habit(id, name, description, newPriority, status, createdAt, completedAt, deadline, streak, lastCompletedDate, userId);
    }

    public boolean isHighPriority() {
        return priority == Priority.HIGH || priority == Priority.CRITICAL;
    }

    public boolean isStreakActive() {
        return streak >= 3;
    }

    public boolean isOverdue() {
        return status != Status.COMPLETED && LocalDateTime.now().isAfter(deadline);
    }

    public long getHoursRemaining() {
        if (status == Status.COMPLETED) return 0;
        return ChronoUnit.HOURS.between(LocalDateTime.now(), deadline);
    }

    public String getDeadlineStatus() {
        if (status == Status.COMPLETED) return "✅";
        long hours = getHoursRemaining();
        if (hours <= 0) return "⏰ ATRASADO";
        if (hours <= 2) return "⚠️ " + hours + "h";
        if (hours <= 6) return "🕐 " + hours + "h";
        return "⏱️ " + hours + "h";
    }
}
