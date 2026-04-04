package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;

public class FilterByPriority implements HabitFilter {
    private final Priority priority;

    public FilterByPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Habit habit) {
        return habit.priority() == priority;
    }

    public Priority getPriority() {
        return priority;
    }
}
