package com.stayhard.service;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.HabitNotFoundException;
import com.stayhard.domain.observer.HabitObserver;
import com.stayhard.domain.strategy.HabitFilter;
import com.stayhard.repository.HabitRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HabitService {

    private final HabitRepository habitRepository;
    private final List<Habit> habits;
    private final List<HabitObserver> observers;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        this.habits = new ArrayList<>(habitRepository.load());
        this.observers = new ArrayList<>();
    }

    public void addObserver(HabitObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(HabitObserver observer) {
        observers.remove(observer);
    }

    public void addHabit(String name, Priority priority) {
        Habit habit = new Habit(name, priority);
        habits.add(habit);
        habitRepository.save(habits);
        notifyHabitAdded(habit);
    }

    public List<Habit> getAllHabits() {
        return Collections.unmodifiableList(habits);
    }

    public List<Habit> filter(HabitFilter filter) {
        return filter.apply(habits);
    }

    public boolean hasHabits() {
        return !habits.isEmpty();
    }

    public Habit getHabit(int index) {
        validateIndex(index);
        return habits.get(index);
    }

    public void startHabit(int index) {
        Habit habit = getHabit(index);
        Habit updated = habit.start();
        habits.set(index, updated);
        habitRepository.save(habits);
        notifyHabitStarted(updated);
    }

    public void completeHabit(int index) {
        Habit habit = getHabit(index);
        Habit updated = habit.complete();
        habits.set(index, updated);
        habitRepository.save(habits);
        notifyHabitCompleted(updated);
    }

    public void updateHabit(int index, String name, Priority priority) {
        Habit current = getHabit(index);
        Habit updated = new Habit(name, priority, current.status());
        habits.set(index, updated);
        habitRepository.save(habits);
    }

    public void deleteHabit(int index) {
        Habit habit = getHabit(index);
        habits.remove(index);
        habitRepository.save(habits);
        notifyHabitDeleted(habit);
    }

    public void resetHabits() {
        for (int i = 0; i < habits.size(); i++) {
            habits.set(i, habits.get(i).reset());
        }
        habitRepository.save(habits);
    }

    public void finishDay() {
        int completed = (int) countCompletedHabits();
        int total = habits.size();
        boolean allCompleted = allHighCompleted();
        
        resetHabits();
        notifyDayFinished(allCompleted, completed, total);
    }

    public boolean allHighCompleted() {
        if (habits.isEmpty()) {
            return false;
        }
        
        return habits.stream()
                .filter(h -> h.priority() == Priority.HIGH)
                .allMatch(h -> h.status() == Status.DONE);
    }

    public long countHabitsByPriority(Priority priority) {
        return habits.stream()
                .filter(h -> h.priority() == priority)
                .count();
    }

    public long countCompletedHabits() {
        return habits.stream()
                .filter(h -> h.status() == Status.DONE)
                .count();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= habits.size()) {
            throw new HabitNotFoundException(index);
        }
    }

    private void notifyHabitAdded(Habit habit) {
        for (HabitObserver observer : observers) {
            observer.onHabitAdded(habit);
        }
    }

    private void notifyHabitStarted(Habit habit) {
        for (HabitObserver observer : observers) {
            observer.onHabitStarted(habit);
        }
    }

    private void notifyHabitCompleted(Habit habit) {
        for (HabitObserver observer : observers) {
            observer.onHabitCompleted(habit);
        }
    }

    private void notifyHabitDeleted(Habit habit) {
        for (HabitObserver observer : observers) {
            observer.onHabitDeleted(habit);
        }
    }

    private void notifyDayFinished(boolean allCompleted, int completedCount, int totalCount) {
        for (HabitObserver observer : observers) {
            observer.onDayFinished(allCompleted, completedCount, totalCount);
        }
    }
}
