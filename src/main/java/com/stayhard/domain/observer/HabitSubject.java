package com.stayhard.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class HabitSubject {
    private final List<HabitObserver> observers = new ArrayList<>();

    public void addObserver(HabitObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(HabitObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(HabitEvent event) {
        observers.forEach(observer -> observer.onHabitEvent(event));
    }

    public int getObserverCount() {
        return observers.size();
    }
}
