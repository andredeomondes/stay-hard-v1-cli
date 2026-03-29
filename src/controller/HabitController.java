package controller;

import domain.entities.Habit;
import domain.enums.Priority;
import service.HabitService;

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

    public boolean hasHabits() {
        return habitService.hasHabits();
    }

    public boolean startHabit(int index) {
        return habitService.startHabit(index);
    }

    public boolean completeHabit(int index) {
        return habitService.completeHabit(index);
    }

    public boolean updateHabit(int index, String name, Priority priority) {
        return habitService.updateHabit(index, name, priority);
    }

    public boolean deleteHabit(int index) {
        return habitService.deleteHabit(index);
    }

    public void resetHabits() {
        habitService.resetHabits();
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