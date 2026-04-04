package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;

import java.util.List;

public class FilterByPriority implements HabitFilter {
    
    private final Priority priority;
    
    public FilterByPriority(Priority priority) {
        this.priority = priority;
    }
    
    @Override
    public List<Habit> apply(List<Habit> habits) {
        return habits.stream()
                .filter(h -> h.priority() == priority)
                .toList();
    }
}
