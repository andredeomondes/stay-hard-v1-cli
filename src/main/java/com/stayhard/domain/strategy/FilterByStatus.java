package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Status;

import java.util.List;

public class FilterByStatus implements HabitFilter {
    
    private final Status status;
    
    public FilterByStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public List<Habit> apply(List<Habit> habits) {
        return habits.stream()
                .filter(h -> h.status() == status)
                .toList();
    }
}
