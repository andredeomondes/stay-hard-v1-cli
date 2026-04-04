package com.stayhard.domain.observer;

public interface HabitObserver {
    void onHabitEvent(HabitEvent event);
    String getObserverName();
}
