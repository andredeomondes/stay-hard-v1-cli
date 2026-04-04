package com.stayhard.ui;

import com.stayhard.controller.HabitController;
import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.HabitNotFoundException;
import com.stayhard.domain.strategy.FilterByPriority;
import com.stayhard.domain.strategy.FilterByStatus;
import com.stayhard.domain.strategy.SortByPriority;
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
    }

    public void list() {
        ConsoleVisual.printHeader("Lista de Hábitos");

        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        List<Habit> habits = habitController.listHabits();
        printHabits(habits);
    }

    public void filter() {
        ConsoleVisual.printHeader("Filtrar Hábitos");

        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        System.out.println("Tipo de filtro:");
        System.out.println("1 - Por Prioridade");
        System.out.println("2 - Por Status");
        System.out.println("3 - Ordenar por Prioridade");
        System.out.println("0 - Voltar");
        ConsoleVisual.divider();

        int option = input.readInt("Escolha o filtro");

        List<Habit> habits;
        List<Habit> allHabits = habitController.listHabits();

        habits = switch (option) {
            case 1 -> {
                System.out.println("\nPrioridade:");
                System.out.println("1 - LOW");
                System.out.println("2 - MEDIUM");
                System.out.println("3 - HIGH");
                int p = input.readInt("Escolha");
                Priority priority = switch (p) {
                    case 1 -> Priority.LOW;
                    case 2 -> Priority.MEDIUM;
                    case 3 -> Priority.HIGH;
                    default -> null;
                };
                if (priority == null) {
                    ConsoleVisual.error("Prioridade inválida.");
                    yield allHabits;
                }
                yield habitController.filter(new FilterByPriority(priority));
            }
            case 2 -> {
                System.out.println("\nStatus:");
                System.out.println("1 - TODO");
                System.out.println("2 - IN_PROGRESS");
                System.out.println("3 - DONE");
                int s = input.readInt("Escolha");
                Status status = switch (s) {
                    case 1 -> Status.TODO;
                    case 2 -> Status.IN_PROGRESS;
                    case 3 -> Status.DONE;
                    default -> null;
                };
                if (status == null) {
                    ConsoleVisual.error("Status inválido.");
                    yield allHabits;
                }
                yield habitController.filter(new FilterByStatus(status));
            }
            case 3 -> habitController.filter(new SortByPriority());
            default -> allHabits;
        };

        ConsoleVisual.printHeader("Resultado do Filtro");
        if (habits.isEmpty()) {
            ConsoleVisual.alert("Nenhum hábito encontrado com este filtro.");
        } else {
            printHabits(habits);
        }
    }

    private void printHabits(List<Habit> habits) {
        habits.forEach(habit -> {
            int index = habitController.listHabits().indexOf(habit);
            System.out.printf("%d - %s [%s] [%s]%n",
                    index + 1,
                    habit.name(),
                    habit.priority(),
                    habit.status());
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

        try {
            habitController.startHabit(index);
        } catch (HabitNotFoundException e) {
            ConsoleVisual.error(e.getMessage());
        }
    }

    public void complete() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para concluir") - 1;

        try {
            habitController.completeHabit(index);
        } catch (HabitNotFoundException e) {
            ConsoleVisual.error(e.getMessage());
        }
    }

    public void edit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para editar") - 1;

        try {
            Habit current = habitController.getHabit(index);

            ConsoleVisual.printHeader("Editar Hábito");

            String newName = input.readString("Novo nome (ENTER para manter)");
            if (newName.isBlank()) {
                newName = current.name();
            }

            var newPriority = input.readPriority();
            if (newPriority == null) {
                newPriority = current.priority();
            }

            habitController.updateHabit(index, newName, newPriority);
            ConsoleVisual.success("Hábito atualizado com sucesso.");
        } catch (HabitNotFoundException e) {
            ConsoleVisual.error(e.getMessage());
        }
    }

    public void remove() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        list();
        int index = input.readInt("Escolha o número do hábito para remover") - 1;

        try {
            habitController.deleteHabit(index);
        } catch (HabitNotFoundException e) {
            ConsoleVisual.error(e.getMessage());
        }
    }
}
