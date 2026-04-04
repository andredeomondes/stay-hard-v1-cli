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
    @DisplayName("Deve adicionar um hábito")
    void deveAdicionarHabit() {
        habitService.addHabit("Estudar Java", Priority.HIGH);

        List<Habit> habits = habitService.getAllHabits();
        assertEquals(1, habits.size());
        assertEquals("Estudar Java", habits.get(0).getName());
        assertEquals(Priority.HIGH, habits.get(0).getPriority());
        assertEquals(Status.TODO, habits.get(0).getStatus());
    }

    @Test
    @DisplayName("Deve iniciar um hábito (TODO → IN_PROGRESS)")
    void deveIniciarHabit() {
        habitService.addHabit("Treinar", Priority.MEDIUM);

        boolean resultado = habitService.startHabit(0);

        assertTrue(resultado);
        assertEquals(Status.IN_PROGRESS, habitService.getAllHabits().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve completar um hábito (TODO → DONE)")
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
        habitService.addHabit("Hábito 1", Priority.HIGH);
        habitService.addHabit("Hábito 2", Priority.MEDIUM);

        boolean resultado = habitService.deleteHabit(0);

        assertTrue(resultado);
        assertEquals(1, habitService.getAllHabits().size());
        assertEquals("Hábito 2", habitService.getAllHabits().get(0).getName());
    }

    @Test
    @DisplayName("Deve contar hábitos concluídos")
    void deveContarHabitsConcluidos() {
        habitService.addHabit("Hábito 1", Priority.HIGH);
        habitService.addHabit("Hábito 2", Priority.MEDIUM);
        habitService.addHabit("Hábito 3", Priority.LOW);

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
        habitService.addHabit("Hábito", Priority.HIGH);
        habitService.completeHabit(0);

        habitService.resetHabits();

        assertEquals(Status.TODO, habitService.getAllHabits().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve salvar no repository ao adicionar hábito")
    void deveSalvarAoAdicionar() {
        habitService.addHabit("Teste", Priority.LOW);

        verify(habitRepository, times(1)).save(anyList());
    }

    @Test
    @DisplayName("Deve salvar no repository ao completar hábito")
    void deveSalvarAoCompletar() {
        habitService.addHabit("Teste", Priority.LOW);
        habitService.completeHabit(0);

        verify(habitRepository, times(2)).save(anyList());
    }
}
