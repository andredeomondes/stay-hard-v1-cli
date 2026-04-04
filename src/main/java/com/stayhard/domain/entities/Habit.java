package com.stayhard.domain.entities;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;

public record Habit(String name, Priority priority, Status status) {

    public Habit {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do hábito não pode ser vazio");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Prioridade não pode ser nula");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
    }

    public Habit(String name, Priority priority) {
        this(name, priority, Status.TODO);
    }

    public Habit withName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Nome do hábito não pode ser vazio");
        }
        return new Habit(newName, priority, status);
    }

    public Habit withPriority(Priority newPriority) {
        if (newPriority == null) {
            throw new IllegalArgumentException("Prioridade não pode ser nula");
        }
        return new Habit(name, newPriority, status);
    }

    public Habit withStatus(Status newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        return new Habit(name, priority, newStatus);
    }

    public Habit start() {
        if (status == Status.TODO) {
            return withStatus(Status.IN_PROGRESS);
        }
        return this;
    }

    public Habit complete() {
        return withStatus(Status.DONE);
    }

    public Habit reset() {
        return withStatus(Status.TODO);
    }
}
