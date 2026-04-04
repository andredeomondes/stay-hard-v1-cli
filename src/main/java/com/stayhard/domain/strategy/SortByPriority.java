package com.stayhard.domain.strategy;

import com.stayhard.domain.entities.Habit;

import java.util.Comparator;

public class SortByPriority implements Comparator<Habit> {
    @Override
    public int compare(Habit h1, Habit h2) {
        return h2.priority().getWeight() - h1.priority().getWeight();
    }

    public static Comparator<Habit> reversedOrder() {
        return Comparator.comparing((Habit h) -> h.priority().getWeight()).reversed();
    }
}
