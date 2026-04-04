package com.stayhard.domain.observer;

import com.stayhard.domain.entities.Habit;

public interface HabitObserver {
    void onHabitCompleted(Habit habit);
    void onHabitStarted(Habit habit);
    void onHabitDeleted(Habit habit);
    void onHabitAdded(Habit habit);
    void onDayFinished(boolean allCompleted, int completedCount, int totalCount);
}
