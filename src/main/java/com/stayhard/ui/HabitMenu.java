package com.stayhard.ui;

import com.stayhard.controller.HabitController;
import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.utils.ConsoleVisual;

import java.util.List;

public class HabitMenu {

    private final HabitController habitController;
    private final InputReader input;

    public HabitMenu(HabitController habitController, InputReader input) {
        this.habitController = habitController;
        this.input = input;
    }

    public void create() {
        ConsoleVisual.printHeader("Criar Hábito");

        String name = input.readString("Nome do hábito");

        if (name.isBlank()) {
            ConsoleVisual.error("O nome do hábito não pode ser vazio.");
            return;
        }

        var priority = input.readPriority();

        if (priority == null) {
            ConsoleVisual.error("Prioridade inválida.");
            return;
        }

        habitController.createHabit(name, priority);
        ConsoleVisual.success("Hábito criado com sucesso.");
    }

    public void list() {
        ConsoleVisual.printHeader("Lista de Hábitos");

        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        List<Habit> habits = habitController.listHabits();

        habits.forEach(habit -> {
            int index = habits.indexOf(habit);
            System.out.printf("%d - %s [%s] [%s]%n",
                    index + 1,
                    habit.getName(),
                    habit.getPriority(),
                    habit.getStatus());
        });

        ConsoleVisual.divider();
        ConsoleVisual.info("Total de hábitos: " + habits.size());
    }

    public void start() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para iniciar") - 1;

        if (habitController.startHabit(index)) {
            ConsoleVisual.success("Hábito iniciado com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }

    public void complete() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para concluir") - 1;

        if (habitController.completeHabit(index)) {
            ConsoleVisual.success("Hábito concluído com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }

    public void edit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para editar") - 1;

        if (index < 0 || index >= habitController.listHabits().size()) {
            ConsoleVisual.error("Índice inválido.");
            return;
        }

        ConsoleVisual.printHeader("Editar Hábito");

        String newName = input.readString("Novo nome (ENTER para manter)");
        Habit current = habitController.listHabits().get(index);

        if (newName.isBlank()) {
            newName = current.getName();
        }

        var newPriority = input.readPriority();
        if (newPriority == null) {
            newPriority = current.getPriority();
        }

        if (habitController.updateHabit(index, newName, newPriority)) {
            ConsoleVisual.success("Hábito atualizado com sucesso.");
        } else {
            ConsoleVisual.error("Erro ao atualizar hábito.");
        }
    }

    public void remove() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para remover") - 1;

        if (habitController.deleteHabit(index)) {
            ConsoleVisual.success("Hábito removido com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }
}
