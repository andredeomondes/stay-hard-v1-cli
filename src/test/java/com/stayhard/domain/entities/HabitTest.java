package com.stayhard.domain.entities;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    @Test
    @DisplayName("Deve criar hábito com nome e prioridade")
    void deveCriarHabit() {
        Habit habit = new Habit("Estudar Java", Priority.HIGH);

        assertEquals("Estudar Java", habit.getName());
        assertEquals(Priority.HIGH, habit.getPriority());
        assertEquals(Status.TODO, habit.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Habit(null, Priority.HIGH);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Habit("", Priority.HIGH);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é espaço em branco")
    void deveLancarExcecaoQuandoNomeEspaco() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Habit("   ", Priority.HIGH);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando prioridade é nula")
    void deveLancarExcecaoQuandoPrioridadeNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Habit("Teste", null);
        });
    }

    @Test
    @DisplayName("Deve iniciar hábito (TODO → IN_PROGRESS)")
    void deveIniciarHabit() {
        Habit habit = new Habit("Teste", Priority.LOW);

        habit.start();

        assertEquals(Status.IN_PROGRESS, habit.getStatus());
    }

    @Test
    @DisplayName("Deve completar hábito (TODO → DONE)")
    void deveCompletarHabit() {
        Habit habit = new Habit("Teste", Priority.LOW);

        habit.complete();

        assertEquals(Status.DONE, habit.getStatus());
    }

    @Test
    @DisplayName("Deve resetar hábito (DONE → TODO)")
    void deveResetarHabit() {
        Habit habit = new Habit("Teste", Priority.LOW);
        habit.complete();

        habit.reset();

        assertEquals(Status.TODO, habit.getStatus());
    }

    @Test
    @DisplayName("Não deve iniciar hábito já completo")
    void deveNaoIniciarHabitJaCompleto() {
        Habit habit = new Habit("Teste", Priority.LOW);
        habit.complete();

        habit.start();

        assertEquals(Status.DONE, habit.getStatus());
    }

    @Test
    @DisplayName("Deve alterar nome do hábito")
    void deveAlterarNome() {
        Habit habit = new Habit("Nome Antigo", Priority.HIGH);

        habit.setName("Nome Novo");

        assertEquals("Nome Novo", habit.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar nome para vazio")
    void deveLancarExcecaoAoAlterarNomeParaVazio() {
        Habit habit = new Habit("Nome", Priority.HIGH);

        assertThrows(IllegalArgumentException.class, () -> {
            habit.setName("");
        });
    }

    @Test
    @DisplayName("Deve alterar prioridade do hábito")
    void deveAlterarPrioridade() {
        Habit habit = new Habit("Nome", Priority.LOW);

        habit.setPriority(Priority.HIGH);

        assertEquals(Priority.HIGH, habit.getPriority());
    }

    @Test
    @DisplayName("Deve restaurar status do hábito")
    void deveRestaurarStatus() {
        Habit habit = new Habit("Nome", Priority.HIGH);
        habit.complete();

        habit.restoreStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, habit.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao restaurar status nulo")
    void deveLancarExcecaoAoRestaurarStatusNulo() {
        Habit habit = new Habit("Nome", Priority.HIGH);

        assertThrows(IllegalArgumentException.class, () -> {
            habit.restoreStatus(null);
        });
    }
}
