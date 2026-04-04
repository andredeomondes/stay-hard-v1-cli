package com.stayhard.service;

import com.stayhard.domain.entities.User;
import com.stayhard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        when(userRepository.load()).thenReturn(new User("Player"));
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Deve carregar usuário do repository")
    void deveCarregarUsuario() {
        User user = userService.getUser();

        assertNotNull(user);
        assertEquals("Player", user.name());
    }

    @Test
    @DisplayName("Deve registrar dia completado")
    void deveRegistrarDiaCompletado() {
        userService.registerCompletedDay();

        User updated = userService.getUser();
        assertEquals(1, updated.daysCompleted());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve incrementar streak ao registrar dia completado")
    void deveIncrementarStreak() {
        userService.registerCompletedDay();
        userService.registerCompletedDay();

        User updated = userService.getUser();
        assertEquals(2, updated.currentStreak());
    }

    @Test
    @DisplayName("Deve atualizar maxStreak quando currentStreak aumenta")
    void deveAtualizarMaxStreak() {
        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerCompletedDay();

        User updated = userService.getUser();
        assertEquals(3, updated.maxStreak());
        assertEquals(3, updated.currentStreak());
    }

    @Test
    @DisplayName("Deve registrar dia falhou")
    void deveRegistrarDiaFalhou() {
        userService.registerFailedDay();

        User updated = userService.getUser();
        assertEquals(1, updated.daysFailed());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve zerar streak ao registrar dia falhou")
    void deveZerarStreakAoFalhar() {
        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerFailedDay();

        User updated = userService.getUser();
        assertEquals(0, updated.currentStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak após falhar")
    void deveManterMaxStreakAposFalhar() {
        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerFailedDay();

        User updated = userService.getUser();
        assertEquals(3, updated.maxStreak());
        assertEquals(0, updated.currentStreak());
    }
}
