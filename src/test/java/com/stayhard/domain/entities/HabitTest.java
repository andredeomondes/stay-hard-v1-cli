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

        assertEquals("Estudar Java", habit.name());
        assertEquals(Priority.HIGH, habit.priority());
        assertEquals(Status.TODO, habit.status());
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

        Habit novo = habit.start();

        assertEquals(Status.IN_PROGRESS, novo.status());
    }

    @Test
    @DisplayName("Deve completar hábito (TODO → DONE)")
    void deveCompletarHabit() {
        Habit habit = new Habit("Teste", Priority.LOW);

        Habit novo = habit.complete();

        assertEquals(Status.DONE, novo.status());
    }

    @Test
    @DisplayName("Deve resetar hábito (DONE → TODO)")
    void deveResetarHabit() {
        Habit habit = new Habit("Teste", Priority.LOW).complete();

        Habit novo = habit.reset();

        assertEquals(Status.TODO, novo.status());
    }

    @Test
    @DisplayName("Não deve iniciar hábito já completo")
    void deveNaoIniciarHabitJaCompleto() {
        Habit habit = new Habit("Teste", Priority.LOW).complete();

        Habit novo = habit.start();

        assertEquals(Status.DONE, novo.status());
    }

    @Test
    @DisplayName("Deve alterar nome do hábito")
    void deveAlterarNome() {
        Habit habit = new Habit("Nome Antigo", Priority.HIGH);

        Habit novo = habit.withName("Nome Novo");

        assertEquals("Nome Novo", novo.name());
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar nome para vazio")
    void deveLancarExcecaoAoAlterarNomeParaVazio() {
        Habit habit = new Habit("Nome", Priority.HIGH);

        assertThrows(IllegalArgumentException.class, () -> {
            habit.withName("");
        });
    }

    @Test
    @DisplayName("Deve alterar prioridade do hábito")
    void deveAlterarPrioridade() {
        Habit habit = new Habit("Nome", Priority.LOW);

        Habit novo = habit.withPriority(Priority.HIGH);

        assertEquals(Priority.HIGH, novo.priority());
    }

    @Test
    @DisplayName("Deve restaurar status do hábito")
    void deveRestaurarStatus() {
        Habit habit = new Habit("Nome", Priority.HIGH).complete();

        Habit novo = habit.withStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, novo.status());
    }

    @Test
    @DisplayName("Deve lançar exceção ao restaurar status nulo")
    void deveLancarExcecaoAoRestaurarStatusNulo() {
        Habit habit = new Habit("Nome", Priority.HIGH);

        assertThrows(IllegalArgumentException.class, () -> {
            habit.withStatus(null);
        });
    }
}
