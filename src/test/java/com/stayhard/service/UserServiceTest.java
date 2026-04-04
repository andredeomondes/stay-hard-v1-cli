package com.stayhard.service;

import com.stayhard.domain.entities.User;
import com.stayhard.domain.exceptions.UserNotFoundException;
import com.stayhard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(repository);
    }

    @Test
    void create_withValidData_shouldReturnCreatedUser() {
        User user = User.create("john", "john@example.com");
        when(repository.save(any(User.class))).thenReturn(user.withId(1L));
        when(repository.existsByUsername("john")).thenReturn(false);
        when(repository.existsByEmail("john@example.com")).thenReturn(false);

        User result = userService.create("john", "john@example.com");

        assertNotNull(result);
        assertEquals("john", result.username());
        verify(repository).save(any(User.class));
    }

    @Test
    void create_withExistingUsername_shouldThrowException() {
        when(repository.existsByUsername("john")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            userService.create("john", "john@example.com")
        );
    }

    @Test
    void login_shouldSetCurrentUser() {
        User user = User.create("john", "john@example.com").withId(1L);
        when(repository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.login("john");

        assertNotNull(userService.getCurrentUser());
        assertEquals("john", result.username());
        assertTrue(userService.isLoggedIn());
    }

    @Test
    void login_withNonExistingUsername_shouldThrowException() {
        when(repository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
            userService.login("unknown")
        );
    }

    @Test
    void logout_shouldClearCurrentUser() {
        User user = User.create("john", "john@example.com").withId(1L);
        when(repository.findByUsername("john")).thenReturn(Optional.of(user));

        userService.login("john");
        userService.logout();

        assertFalse(userService.isLoggedIn());
        assertNull(userService.getCurrentUser());
    }

    @Test
    void addXp_whenLoggedIn_shouldUpdateUser() {
        User user = User.create("john", "john@example.com").withId(1L);
        when(repository.findByUsername("john")).thenReturn(Optional.of(user));
        when(repository.update(any(User.class))).thenReturn(user.addXp(50));

        userService.login("john");
        User result = userService.addXp(50);

        assertEquals(50, result.xp());
    }

    @Test
    void addXp_whenNotLoggedIn_shouldThrowException() {
        assertThrows(IllegalStateException.class, () ->
            userService.addXp(50)
        );
    }

    @Test
    void completeHabit_whenLoggedIn_shouldIncrementHabitsAndXp() {
        User user = User.create("john", "john@example.com").withId(1L);
        when(repository.findByUsername("john")).thenReturn(Optional.of(user));
        when(repository.update(any(User.class))).thenReturn(user.incrementHabitsCompleted().addXp(10));

        userService.login("john");
        User result = userService.completeHabit();

        assertEquals(1, result.totalHabitsCompleted());
        assertEquals(10, result.xp());
    }
}
