package com.stayhard.service;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    private HabitService habitService;

    @BeforeEach
    void setUp() {
        when(habitRepository.load()).thenReturn(new ArrayList<>());
        habitService = new HabitService(habitRepository);
    }

    @Test
    @DisplayName("Deve adicionar um hábito com sucesso")
    void deveAdicionarHabit() {
        habitService.addHabit("Estudar Java", Priority.HIGH);

        assertTrue(habitService.hasHabits());
        assertEquals(1, habitService.getAllHabits().size());
        assertEquals("Estudar Java", habitService.getAllHabits().get(0).getName());
    }

    @Test
    @DisplayName("Deve iniciar um hábito")
    void deveIniciarHabit() {
        habitService.addHabit("Treinar", Priority.MEDIUM);
        boolean resultado = habitService.startHabit(0);

        assertTrue(resultado);
        assertEquals(Status.IN_PROGRESS, habitService.getAllHabits().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve completar um hábito")
    void deveCompletarHabit() {
        habitService.addHabit("Ler livro", Priority.LOW);
        boolean resultado = habitService.completeHabit(0);

        assertTrue(resultado);
        assertEquals(Status.DONE, habitService.getAllHabits().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve retornar false para índice inválido")
    void deveRetornarFalseParaIndiceInvalido() {
        assertFalse(habitService.startHabit(99));
        assertFalse(habitService.completeHabit(99));
        assertFalse(habitService.deleteHabit(99));
    }

    @Test
    @DisplayName("Deve deletar um hábito")
    void deveDeletarHabit() {
        habitService.addHabit("Deletar", Priority.LOW);
        assertTrue(habitService.hasHabits());

        boolean resultado = habitService.deleteHabit(0);

        assertTrue(resultado);
        assertFalse(habitService.hasHabits());
    }

    @Test
    @DisplayName("Deve contar hábitos concluídos")
    void deveContarHabitsConcluidos() {
        habitService.addHabit("Habit 1", Priority.HIGH);
        habitService.addHabit("Habit 2", Priority.MEDIUM);
        habitService.addHabit("Habit 3", Priority.LOW);

        habitService.completeHabit(0);
        habitService.completeHabit(1);

        assertEquals(2, habitService.countCompletedHabits());
    }

    @Test
    @DisplayName("Deve contar hábitos por prioridade")
    void deveContarHabitsPorPrioridade() {
        habitService.addHabit("H1", Priority.HIGH);
        habitService.addHabit("H2", Priority.HIGH);
        habitService.addHabit("H3", Priority.MEDIUM);

        assertEquals(2, habitService.countHabitsByPriority(Priority.HIGH));
        assertEquals(1, habitService.countHabitsByPriority(Priority.MEDIUM));
        assertEquals(0, habitService.countHabitsByPriority(Priority.LOW));
    }

    @Test
    @DisplayName("Deve fazer reset de todos os hábitos")
    void deveResetarHabits() {
        habitService.addHabit("H1", Priority.HIGH);
        habitService.addHabit("H2", Priority.MEDIUM);

        habitService.completeHabit(0);
        habitService.startHabit(1);

        habitService.resetHabits();

        List<Habit> habits = habitService.getAllHabits();
        assertEquals(Status.TODO, habits.get(0).getStatus());
        assertEquals(Status.TODO, habits.get(1).getStatus());
    }
}
