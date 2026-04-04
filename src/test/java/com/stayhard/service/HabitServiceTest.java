package com.stayhard.service;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.HabitNotFoundException;
import com.stayhard.domain.exceptions.InvalidHabitException;
import com.stayhard.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    @Mock
    private HabitRepository repository;

    private HabitService habitService;

    @BeforeEach
    void setUp() {
        habitService = new HabitService(repository);
    }

    @Test
    void create_withValidData_shouldReturnCreatedHabit() {
        Habit habit = Habit.create("Exercise", "Daily workout", Priority.HIGH, 1L);
        when(repository.save(any(Habit.class))).thenReturn(habit.withId(1L));

        Habit result = habitService.create("Exercise", "Daily workout", Priority.HIGH, 1L);

        assertNotNull(result);
        assertEquals("Exercise", result.name());
        verify(repository).save(any(Habit.class));
    }

    @Test
    void create_withBlankName_shouldThrowException() {
        assertThrows(InvalidHabitException.class, () ->
            habitService.create("   ", "desc", Priority.LOW, 1L)
        );
    }

    @Test
    void findById_withExistingId_shouldReturnHabit() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L).withId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(habit));

        Habit result = habitService.findById(1L);

        assertEquals("Exercise", result.name());
    }

    @Test
    void findById_withNonExistingId_shouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(HabitNotFoundException.class, () ->
            habitService.findById(999L)
        );
    }

    @Test
    void findAll_shouldReturnAllHabits() {
        List<Habit> habits = List.of(
            Habit.create("Exercise", "Daily", Priority.HIGH, 1L).withId(1L),
            Habit.create("Read", "Books", Priority.MEDIUM, 1L).withId(2L)
        );
        when(repository.findAll()).thenReturn(habits);

        List<Habit> result = habitService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void complete_shouldMarkHabitAsCompleted() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L).withId(1L);
        Habit completed = habit.markComplete();
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(repository.update(any(Habit.class))).thenReturn(completed);

        Habit result = habitService.complete(1L);

        assertEquals(Status.COMPLETED, result.status());
        assertEquals(1, result.streak());
    }

    @Test
    void incomplete_shouldMarkHabitAsPending() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L)
            .withId(1L)
            .markComplete();
        Habit incomplete = habit.markIncomplete();
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(repository.update(any(Habit.class))).thenReturn(incomplete);

        Habit result = habitService.incomplete(1L);

        assertEquals(Status.PENDING, result.status());
        assertEquals(0, result.streak());
    }

    @Test
    void delete_shouldRemoveHabit() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L).withId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        doNothing().when(repository).delete(1L);

        habitService.delete(1L);

        verify(repository).delete(1L);
    }

    @Test
    void filterByPriority_shouldReturnFilteredHabits() {
        List<Habit> habits = List.of(
            Habit.create("Exercise", "Daily", Priority.HIGH, 1L).withId(1L),
            Habit.create("Sleep", "8 hours", Priority.LOW, 1L).withId(2L)
        );
        when(repository.findAll()).thenReturn(habits);

        List<Habit> result = habitService.filterByPriority(Priority.HIGH);

        assertEquals(1, result.size());
        assertEquals(Priority.HIGH, result.get(0).priority());
    }

    @Test
    void getCompletionRate_withHabits_shouldReturnCorrectRate() {
        List<Habit> habits = List.of(
            Habit.create("E1", "D1", Priority.HIGH, 1L).withId(1L).markComplete(),
            Habit.create("E2", "D2", Priority.HIGH, 1L).withId(2L)
        );
        when(repository.findAll()).thenReturn(habits);
        when(repository.count()).thenReturn(2);

        double rate = habitService.getCompletionRate();

        assertEquals(50.0, rate, 0.01);
    }

    @Test
    void getCompletionRate_withNoHabits_shouldReturnZero() {
        when(repository.count()).thenReturn(0);

        double rate = habitService.getCompletionRate();

        assertEquals(0.0, rate);
    }
}
