package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;

import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
public interface HabitFilter extends Predicate<Habit> {
    default HabitFilter and(HabitFilter other) {
        return habit -> this.test(habit) && other.test(habit);
    }

    default HabitFilter or(HabitFilter other) {
        return habit -> this.test(habit) || other.test(habit);
    }

    default HabitFilter negate() {
        return habit -> !this.test(habit);
    }

    static HabitFilter all() {
        return habit -> true;
    }

    static HabitFilter none() {
        return habit -> false;
    }

    static List<Habit> apply(List<Habit> habits, HabitFilter filter) {
        return habits.stream().filter(filter).toList();
    }
}
