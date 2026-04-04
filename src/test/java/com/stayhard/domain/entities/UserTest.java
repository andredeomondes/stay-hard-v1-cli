package com.stayhard.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Deve criar usuário com nome")
    void deveCriarUsuario() {
        User user = new User("Andre");

        assertEquals("Andre", user.name());
        assertEquals(0, user.daysCompleted());
        assertEquals(0, user.daysFailed());
        assertEquals(0, user.currentStreak());
        assertEquals(0, user.maxStreak());
    }

    @Test
    @DisplayName("Deve criar usuário com parâmetros completos")
    void deveCriarUsuarioCompleto() {
        User user = new User("Andre", 10, 2, 5, 20);

        assertEquals("Andre", user.name());
        assertEquals(10, user.daysCompleted());
        assertEquals(2, user.daysFailed());
        assertEquals(5, user.currentStreak());
        assertEquals(20, user.maxStreak());
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

        User novo = user.addCompletedDay();

        assertEquals(1, novo.daysCompleted());
    }

    @Test
    @DisplayName("Deve incrementar currentStreak ao adicionar dia")
    void deveIncrementarCurrentStreak() {
        User user = new User("Andre");

        User u1 = user.addCompletedDay();
        User u2 = u1.addCompletedDay();
        User u3 = u2.addCompletedDay();

        assertEquals(3, u3.currentStreak());
    }

    @Test
    @DisplayName("Deve atualizar maxStreak quando currentStreak é maior")
    void deveAtualizarMaxStreak() {
        User user = new User("Andre");

        User u1 = user.addCompletedDay();
        User u2 = u1.addCompletedDay();
        User u3 = u2.addCompletedDay();

        assertEquals(3, u3.maxStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak quando currentStreak diminui")
    void deveManterMaxStreak() {
        User user = new User("Andre", 0, 0, 5, 10);

        User novo = user.addCompletedDay();

        assertEquals(10, novo.maxStreak());
    }

    @Test
    @DisplayName("Deve adicionar dia falhou")
    void deveAdicionarDiaFalhou() {
        User user = new User("Andre");

        User novo = user.addFailedDay();

        assertEquals(1, novo.daysFailed());
    }

    @Test
    @DisplayName("Deve zerar currentStreak ao falhar")
    void deveZerarCurrentStreak() {
        User user = new User("Andre");

        User u1 = user.addCompletedDay();
        User u2 = u1.addCompletedDay();
        User u3 = u2.addFailedDay();

        assertEquals(0, u3.currentStreak());
    }

    @Test
    @DisplayName("Deve manter maxStreak após falhar")
    void deveManterMaxStreakAposFalhar() {
        User user = new User("Andre", 0, 0, 0, 10);

        User u1 = user.addCompletedDay();
        User u2 = u1.addCompletedDay();
        User u3 = u2.addFailedDay();

        assertEquals(10, u3.maxStreak());
    }
}
