package com.stayhard.controller;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.strategy.HabitFilter;
import com.stayhard.service.HabitService;

import java.util.List;

public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    public void createHabit(String name, Priority priority) {
        habitService.addHabit(name, priority);
    }

    public List<Habit> listHabits() {
        return habitService.getAllHabits();
    }

    public List<Habit> filter(HabitFilter filter) {
        return habitService.filter(filter);
    }

    public boolean hasHabits() {
        return habitService.hasHabits();
    }

    public Habit getHabit(int index) {
        return habitService.getHabit(index);
    }

    public void startHabit(int index) {
        habitService.startHabit(index);
    }

    public void completeHabit(int index) {
        habitService.completeHabit(index);
    }

    public void updateHabit(int index, String name, Priority priority) {
        habitService.updateHabit(index, name, priority);
    }

    public void deleteHabit(int index) {
        habitService.deleteHabit(index);
    }

    public void resetHabits() {
        habitService.resetHabits();
    }

    public void finishDay() {
        habitService.finishDay();
    }

    public boolean allHighCompleted() {
        return habitService.allHighCompleted();
    }

    public long countCompletedHabits() {
        return habitService.countCompletedHabits();
    }

    public long countHabitsByPriority(Priority priority) {
        return habitService.countHabitsByPriority(priority);
    }
}
