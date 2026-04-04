package com.stayhard.domain.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create_withValidData_shouldCreateUser() {
        User user = User.create("john", "john@example.com");

        assertNotNull(user);
        assertEquals("john", user.username());
        assertEquals("john@example.com", user.email());
        assertEquals(LocalDate.now(), user.createdAt());
        assertEquals(1, user.level());
        assertEquals(0, user.xp());
        assertEquals(0, user.totalHabitsCompleted());
    }

    @Test
    void create_withBlankUsername_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            User.create("   ", "john@example.com")
        );
    }

    @Test
    void create_withInvalidEmail_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
            User.create("john", "invalid-email")
        );
    }

    @Test
    void addXp_shouldIncrementXp() {
        User user = User.create("john", "john@example.com");

        User withXp = user.addXp(50);

        assertEquals(50, withXp.xp());
        assertEquals(1, withXp.level());
    }

    @Test
    void addXp_shouldLevelUpWhenReaching100() {
        User user = User.create("john", "john@example.com");

        User leveledUp = user.addXp(150);

        assertEquals(2, leveledUp.level());
        assertEquals(50, leveledUp.xp());
    }

    @Test
    void addXp_shouldHandleMultipleLevelUps() {
        User user = User.create("john", "john@example.com").withId(1L);

        User multiLevel = user.addXp(350);

        assertEquals(4, multiLevel.level());
        assertEquals(50, multiLevel.xp());
    }

    @Test
    void incrementHabitsCompleted_shouldIncrementCounter() {
        User user = User.create("john", "john@example.com");

        User updated = user.incrementHabitsCompleted();

        assertEquals(1, updated.totalHabitsCompleted());
    }

    @Test
    void getXpToNextLevel_shouldReturnCorrectValue() {
        User user = User.create("john", "john@example.com").withId(1L);
        User withXp = user.addXp(30);

        assertEquals(70, withXp.getXpToNextLevel());
    }

    @Test
    void getLevelProgress_shouldReturnCorrectPercentage() {
        User user = User.create("john", "john@example.com").withId(1L);
        User withXp = user.addXp(75);

        assertEquals(0.75, withXp.getLevelProgress(), 0.01);
    }

    @Test
    void withId_shouldCreateNewUserWithId() {
        User user = User.create("john", "john@example.com");

        User withId = user.withId(100L);

        assertEquals(100L, withId.id());
        assertEquals(user.username(), withId.username());
    }
}
