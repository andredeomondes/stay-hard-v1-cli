package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Status;

public class FilterByStatus implements HabitFilter {
    private final Status status;

    public FilterByStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean test(Habit habit) {
        return habit.status() == status;
    }

    public Status getStatus() {
        return status;
    }
}
