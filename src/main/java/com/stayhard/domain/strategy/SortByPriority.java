package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;

import java.util.Comparator;
import java.util.List;

public class SortByPriority implements HabitFilter {
    
    private final boolean descending;
    
    public SortByPriority() {
        this(false);
    }
    
    public SortByPriority(boolean descending) {
        this.descending = descending;
    }
    
    @Override
    public List<Habit> apply(List<Habit> habits) {
        Comparator<Habit> comparator = Comparator.comparingInt(
                h -> h.priority().getWeight()
        );
        
        if (descending) {
            comparator = comparator.reversed();
        }
        
        return habits.stream()
                .sorted(comparator)
                .toList();
    }
}
