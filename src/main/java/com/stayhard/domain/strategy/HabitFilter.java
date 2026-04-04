package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;

import java.util.List;

@FunctionalInterface
public interface HabitFilter {
    List<Habit> apply(List<Habit> habits);
}
