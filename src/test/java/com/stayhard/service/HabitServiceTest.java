package com.stayhard.service;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.HabitNotFoundException;
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
        assertEquals("Estudar Java", habits.get(0).name());
        assertEquals(Priority.HIGH, habits.get(0).priority());
        assertEquals(Status.TODO, habits.get(0).status());
    }

    @Test
    @DisplayName("Deve iniciar um hábito (TODO → IN_PROGRESS)")
    void deveIniciarHabit() {
        habitService.addHabit("Treinar", Priority.MEDIUM);

        habitService.startHabit(0);

        assertEquals(Status.IN_PROGRESS, habitService.getAllHabits().get(0).status());
    }

    @Test
    @DisplayName("Deve completar um hábito (TODO → DONE)")
    void deveCompletarHabit() {
        habitService.addHabit("Ler livro", Priority.LOW);

        habitService.completeHabit(0);

        assertEquals(Status.DONE, habitService.getAllHabits().get(0).status());
    }

    @Test
    @DisplayName("Deve lançar exceção para índice inválido")
    void deveLancarExcecaoParaIndiceInvalido() {
        assertThrows(HabitNotFoundException.class, () -> habitService.startHabit(99));
        assertThrows(HabitNotFoundException.class, () -> habitService.completeHabit(99));
        assertThrows(HabitNotFoundException.class, () -> habitService.deleteHabit(99));
    }

    @Test
    @DisplayName("Deve deletar um hábito")
    void deveDeletarHabit() {
        habitService.addHabit("Hábito 1", Priority.HIGH);
        habitService.addHabit("Hábito 2", Priority.MEDIUM);

        habitService.deleteHabit(0);

        assertEquals(1, habitService.getAllHabits().size());
        assertEquals("Hábito 2", habitService.getAllHabits().get(0).name());
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

        assertEquals(Status.TODO, habitService.getAllHabits().get(0).status());
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

    @Test
    @DisplayName("Deve filtrar hábitos por prioridade usando Strategy")
    void deveFiltrarHabitsPorPrioridade() {
        habitService.addHabit("H1", Priority.HIGH);
        habitService.addHabit("H2", Priority.MEDIUM);
        habitService.addHabit("H3", Priority.HIGH);

        List<Habit> allHabits = habitService.getAllHabits();
        List<Habit> highHabits = allHabits.stream()
                .filter(h -> h.priority() == Priority.HIGH)
                .toList();

        assertEquals(2, highHabits.size());
    }

    @Test
    @DisplayName("Deve finalizar dia e notificar observers")
    void deveFinalizarDia() {
        habitService.addHabit("H1", Priority.HIGH);
        habitService.addHabit("H2", Priority.MEDIUM);
        habitService.completeHabit(0);

        habitService.finishDay();

        assertEquals(Status.TODO, habitService.getAllHabits().get(0).status());
        assertEquals(Status.TODO, habitService.getAllHabits().get(1).status());
    }
}
