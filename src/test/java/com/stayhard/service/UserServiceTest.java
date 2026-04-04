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
        assertEquals("Player", user.getName());
    }

    @Test
    @DisplayName("Deve registrar dia completado")
    void deveRegistrarDiaCompletado() {
        User user = userService.getUser();
        int antes = user.getDaysCompleted();

        userService.registerCompletedDay();

        assertEquals(antes + 1, user.getDaysCompleted());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Deve incrementar streak ao registrar dia completado")
    void deveIncrementarStreak() {
        User user = userService.getUser();

        userService.registerCompletedDay();
        userService.registerCompletedDay();

        assertEquals(2, user.getCurrentStreak());
    }

    @Test
    @DisplayName("Deve atualizar maxStreak quando currentStreak aumenta")
    void deveAtualizarMaxStreak() {
        User user = userService.getUser();

        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerCompletedDay();

        assertEquals(3, user.getMaxStreak());
        assertEquals(3, user.getCurrentStreak());
    }

    @Test
    @DisplayName("Deve registrar dia falhou")
    void deveRegistrarDiaFalhou() {
        User user = userService.getUser();
        int antes = user.getDaysFailed();

        userService.registerFailedDay();

        assertEquals(antes + 1, user.getDaysFailed());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Deve zerar streak ao registrar dia falhou")
    void deveZerarStreakAoFalhar() {
        User user = userService.getUser();

        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerFailedDay();

        assertEquals(0, user.getCurrentStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak após falhar")
    void deveManterMaxStreakAposFalhar() {
        User user = userService.getUser();

        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerCompletedDay();
        userService.registerFailedDay();

        assertEquals(3, user.getMaxStreak());
        assertEquals(0, user.getCurrentStreak());
    }
}
