package repository;

import com.stayhard.domain.entities.Habit;

import java.util.List;

public interface HabitRepository {
    List<Habit> load();
    void save(List<Habit> habits);
}