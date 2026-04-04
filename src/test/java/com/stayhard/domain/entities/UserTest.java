package com.stayhard.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Deve criar usuário com nome")
    void deveCriarUsuario() {
        User user = new User("Andre");

        assertEquals("Andre", user.getName());
        assertEquals(0, user.getDaysCompleted());
        assertEquals(0, user.getDaysFailed());
        assertEquals(0, user.getCurrentStreak());
        assertEquals(0, user.getMaxStreak());
    }

    @Test
    @DisplayName("Deve criar usuário com parâmetros completos")
    void deveCriarUsuarioCompleto() {
        User user = new User("Andre", 10, 2, 5, 20);

        assertEquals("Andre", user.getName());
        assertEquals(10, user.getDaysCompleted());
        assertEquals(2, user.getDaysFailed());
        assertEquals(5, user.getCurrentStreak());
        assertEquals(20, user.getMaxStreak());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("");
        });
    }

    @Test
    @DisplayName("Deve adicionar dia completado")
    void deveAdicionarDiaCompletado() {
        User user = new User("Andre");

        user.addCompletedDay();

        assertEquals(1, user.getDaysCompleted());
    }

    @Test
    @DisplayName("Deve incrementar currentStreak ao adicionar dia")
    void deveIncrementarCurrentStreak() {
        User user = new User("Andre");

        user.addCompletedDay();
        user.addCompletedDay();
        user.addCompletedDay();

        assertEquals(3, user.getCurrentStreak());
    }

    @Test
    @DisplayName("Deve atualizar maxStreak quando currentStreak é maior")
    void deveAtualizarMaxStreak() {
        User user = new User("Andre");

        user.addCompletedDay();
        user.addCompletedDay();
        user.addCompletedDay();

        assertEquals(3, user.getMaxStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak quando currentStreak diminui")
    void deveManterMaxStreak() {
        User user = new User("Andre", 0, 0, 5, 10);

        user.addCompletedDay();

        assertEquals(10, user.getMaxStreak());
    }

    @Test
    @DisplayName("Deve adicionar dia falhou")
    void deveAdicionarDiaFalhou() {
        User user = new User("Andre");

        user.addFailedDay();

        assertEquals(1, user.getDaysFailed());
    }

    @Test
    @DisplayName("Deve zerar currentStreak ao falhar")
    void deveZerarCurrentStreak() {
        User user = new User("Andre");

        user.addCompletedDay();
        user.addCompletedDay();
        user.addFailedDay();

        assertEquals(0, user.getCurrentStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak após falhar")
    void deveManterMaxStreakAposFalhar() {
        User user = new User("Andre", 0, 0, 0, 10);

        user.addCompletedDay();
        user.addCompletedDay();
        user.addFailedDay();

        assertEquals(10, user.getMaxStreak());
    }
}
