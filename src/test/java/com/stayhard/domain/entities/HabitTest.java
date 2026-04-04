package com.stayhard.domain.entities;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    @Test
    void create_withValidData_shouldCreateHabit() {
        Habit habit = Habit.create("Exercise", "Daily workout", Priority.HIGH, 1L);

        assertNotNull(habit);
        assertEquals("Exercise", habit.name());
        assertEquals("Daily workout", habit.description());
        assertEquals(Priority.HIGH, habit.priority());
        assertEquals(Status.PENDING, habit.status());
        assertEquals(LocalDate.now(), habit.createdAt());
        assertEquals(0, habit.streak());
        assertNull(habit.lastCompletedDate());
    }

    @Test
    void create_withBlankName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            Habit.create("   ", "desc", Priority.LOW, 1L)
        );
    }

    @Test
    void create_withNullName_shouldThrowException() {
        assertThrows(NullPointerException.class, () ->
            Habit.create(null, "desc", Priority.LOW, 1L)
        );
    }

    @Test
    void markComplete_shouldUpdateStatusAndSetLastCompletedDate() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);

        Habit completed = habit.markComplete();

        assertEquals(Status.COMPLETED, completed.status());
        assertNotNull(completed.completedAt());
        assertEquals(LocalDate.now(), completed.lastCompletedDate());
        assertEquals(0, completed.streak());
    }

    @Test
    void markIncomplete_shouldResetStatusAndStreak() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L)
            .markComplete();

        Habit incomplete = habit.markIncomplete();

        assertEquals(Status.PENDING, incomplete.status());
        assertNull(incomplete.completedAt());
        assertEquals(0, incomplete.streak());
    }

    @Test
    void renewForNewDay_shouldIncrementStreakIfCompleted() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L)
            .markComplete();

        Habit renewed = habit.renewForNewDay();

        assertEquals(Status.PENDING, renewed.status());
        assertEquals(1, renewed.streak());
        assertNull(renewed.lastCompletedDate());
    }

    @Test
    void renewForNewDay_shouldResetStreakIfIncomplete() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);

        Habit renewed = habit.renewForNewDay();

        assertEquals(Status.PENDING, renewed.status());
        assertEquals(0, renewed.streak());
    }

    @Test
    void isHighPriority_withHighOrCritical_shouldReturnTrue() {
        Habit high = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);
        Habit critical = Habit.create("Exercise", "Daily", Priority.CRITICAL, 1L);
        Habit low = Habit.create("Exercise", "Daily", Priority.LOW, 1L);

        assertTrue(high.isHighPriority());
        assertTrue(critical.isHighPriority());
        assertFalse(low.isHighPriority());
    }

    @Test
    void isStreakActive_withStreak3OrMore_shouldReturnTrue() {
        Habit active = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);
        
        Habit withStreak3 = new Habit(
            active.id(), active.name(), active.description(),
            active.priority(), active.status(), active.createdAt(),
            active.completedAt(), active.deadline(), 3, LocalDate.now(), active.userId()
        );

        Habit inactive = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);

        assertTrue(withStreak3.isStreakActive());
        assertFalse(inactive.isStreakActive());
    }

    @Test
    void withId_shouldCreateNewHabitWithId() {
        Habit habit = Habit.create("Exercise", "Daily", Priority.HIGH, 1L);

        Habit withId = habit.withId(100L);

        assertEquals(100L, withId.id());
        assertEquals(habit.name(), withId.name());
    }
}
