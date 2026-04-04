package com.stayhard.service;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.HabitNotFoundException;
import com.stayhard.domain.exceptions.InvalidHabitException;
import com.stayhard.domain.observer.ConsoleObserver;
import com.stayhard.domain.observer.HabitEvent;
import com.stayhard.domain.observer.HabitSubject;
import com.stayhard.domain.strategy.HabitFilter;
import com.stayhard.repository.HabitRepository;

import java.util.Comparator;
import java.util.List;

public class HabitService {
    private final HabitRepository repository;
    private final HabitSubject subject;

    public HabitService(HabitRepository repository) {
        this.repository = repository;
        this.subject = new HabitSubject();
        this.subject.addObserver(new ConsoleObserver());
    }

    public Habit create(String name, String description, Priority priority, Long userId) {
        if (name == null || name.isBlank()) {
            throw InvalidHabitException.emptyName();
        }

        Habit habit = Habit.create(name, description, priority, userId);
        Habit saved = repository.save(habit);
        subject.notifyObservers(HabitEvent.created(saved));
        return saved;
    }

    public Habit findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new HabitNotFoundException(id));
    }

    public List<Habit> findAll() {
        return repository.findAll();
    }

    public List<Habit> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Habit complete(Long id) {
        Habit habit = findById(id);
        Habit updated = habit.markComplete();
        Habit result = repository.update(updated);
        subject.notifyObservers(HabitEvent.completed(result));
        return result;
    }

    public Habit incomplete(Long id) {
        Habit habit = findById(id);
        Habit updated = habit.markIncomplete();
        Habit result = repository.update(updated);
        subject.notifyObservers(HabitEvent.updated(result));
        return result;
    }

    public Habit update(Long id, String name, String description, Priority priority) {
        Habit habit = findById(id);

        if (name != null && name.isBlank()) {
            throw InvalidHabitException.emptyName();
        }

        Habit updated = new Habit(
            habit.id(),
            name != null ? name.trim() : habit.name(),
            description != null ? description.trim() : habit.description(),
            priority != null ? priority : habit.priority(),
            habit.status(),
            habit.createdAt(),
            habit.completedAt(),
            habit.streak(),
            habit.userId()
        );

        Habit result = repository.update(updated);
        subject.notifyObservers(HabitEvent.updated(result));
        return result;
    }

    public void delete(Long id) {
        findById(id);
        repository.delete(id);
        subject.notifyObservers(HabitEvent.deleted(id));
    }

    public List<Habit> filterByPriority(Priority priority) {
        HabitFilter filter = habit -> habit.priority() == priority;
        return repository.findAll().stream().filter(filter).toList();
    }

    public List<Habit> filterByStatus(Status status) {
        HabitFilter filter = habit -> habit.status() == status;
        return repository.findAll().stream().filter(filter).toList();
    }

    public List<Habit> getHighPriorityCompleted() {
        HabitFilter filter = habit -> habit.isHighPriority() && habit.status() == Status.COMPLETED;
        return repository.findAll().stream().filter(filter).toList();
    }

    public List<Habit> getSortedByPriority() {
        return repository.findAll().stream()
            .sorted(Comparator.comparing((Habit h) -> h.priority().getWeight()).reversed())
            .toList();
    }

    public void resetAllHabits() {
        repository.findAll().stream()
            .filter(h -> h.status() == Status.COMPLETED)
            .forEach(h -> {
                Habit reset = h.markIncomplete();
                repository.update(reset);
            });
    }

    public int getTotalCount() {
        return repository.count();
    }

    public int getCompletedCount() {
        return (int) repository.findAll().stream()
            .filter(h -> h.status() == Status.COMPLETED)
            .count();
    }

    public double getCompletionRate() {
        int total = repository.count();
        if (total == 0) return 0.0;
        return (double) getCompletedCount() / total * 100;
    }
}
