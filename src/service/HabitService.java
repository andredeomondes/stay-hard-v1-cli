package service;

import domain.entities.Habit;
import domain.enums.Priority;
import domain.enums.Status;
import repository.HabitRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HabitService {

    private final HabitRepository habitRepository;
    private final List<Habit> habits;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        this.habits = new ArrayList<>(habitRepository.load());
    }

    public void addHabit(String name, Priority priority) {
        Habit habit = new Habit(name, priority);
        habits.add(habit);
        habitRepository.save(habits);
    }

    public List<Habit> getAllHabits() {
        return Collections.unmodifiableList(habits);
    }

    public boolean hasHabits() {
        return !habits.isEmpty();
    }

    public boolean startHabit(int index) {
        if (isInvalidIndex(index)) {
            return false;
        }

        habits.get(index).start();
        habitRepository.save(habits);
        return true;
    }

    public boolean completeHabit(int index) {
        if (isInvalidIndex(index)) {
            return false;
        }

        habits.get(index).complete();
        habitRepository.save(habits);
        return true;
    }

    public boolean updateHabit(int index, String name, Priority priority) {
        if (isInvalidIndex(index)) {
            return false;
        }

        Habit habit = habits.get(index);
        habit.setName(name);
        habit.setPriority(priority);
        habitRepository.save(habits);
        return true;
    }

    public boolean deleteHabit(int index) {
        if (isInvalidIndex(index)) {
            return false;
        }

        habits.remove(index);
        habitRepository.save(habits);
        return true;
    }

    public void resetHabits() {
        for (Habit habit : habits) {
            habit.reset();
        }
        habitRepository.save(habits);
    }

    public boolean allHighCompleted() {
        boolean hasHigh = false;

        for (Habit habit : habits) {
            if (habit.getPriority() == Priority.HIGH) {
                hasHigh = true;
                if (habit.getStatus() != Status.DONE) {
                    return false;
                }
            }
        }

        return hasHigh;
    }

    public long countHabitsByPriority(Priority priority) {
        return habits.stream()
                .filter(h -> h.getPriority() == priority)
                .count();
    }

    public long countCompletedHabits() {
        return habits.stream()
                .filter(h -> h.getStatus() == Status.DONE)
                .count();
    }

    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= habits.size();
    }
}