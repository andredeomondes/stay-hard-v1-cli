package com.stayhard.repository;

import com.stayhard.domain.entities.Habit;

import java.util.List;
import java.util.Optional;

public interface HabitRepository {
    Habit save(Habit habit);
    Optional<Habit> findById(Long id);
    List<Habit> findAll();
    List<Habit> findByUserId(Long userId);
    Habit update(Habit habit);
    void delete(Long id);
    int count();
}
