package ui;

import controller.HabitController;
import controller.UserController;
import domain.entities.Habit;
import domain.enums.Priority;
import domain.utils.ConsoleVisual;

import java.util.List;
import java.util.Scanner;

public class UserMenus {

    private final HabitController habitController;
    private final UserController userController;
    private final Scanner scanner;

    public UserMenus(HabitController habitController, UserController userController) {
        this.habitController = habitController;
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option;

        do {
            showMainMenu();
            option = readInt("Escolha uma opção");

            switch (option) {
                case 1 -> createHabit();
                case 2 -> listHabits();
                case 3 -> startHabit();
                case 4 -> completeHabit();
                case 5 -> editHabit();
                case 6 -> removeHabit();
                case 7 -> showStatus();
                case 8 -> finishDay();
                case 0 -> ConsoleVisual.info("Saindo do sistema...");
                default -> ConsoleVisual.error("Opção inválida.");
            }

            if (option != 0) {
                pressEnterToContinue();
            }

        } while (option != 0);
    }

    private void showMainMenu() {
        ConsoleVisual.printHeader("Stay Hard System");

        System.out.println("Day: " + userController.getCurrentDay());
        System.out.println("Streak: " + userController.getUser().getCurrentStreak());
        System.out.println("Level: " + userController.getLevelName()); // 🔥 AQUI

        ConsoleVisual.divider();

        System.out.println("1 - Criar hábito");
        System.out.println("2 - Listar hábitos");
        System.out.println("3 - Iniciar hábito");
        System.out.println("4 - Concluir hábito");
        System.out.println("5 - Editar hábito");
        System.out.println("6 - Remover hábito");
        System.out.println("7 - Ver status");
        System.out.println("8 - Finalizar dia");
        System.out.println("0 - Sair");

        ConsoleVisual.divider();
    }
    private void createHabit() {
        ConsoleVisual.printHeader("Criar Hábito");

        System.out.print(ConsoleVisual.CYAN + ConsoleVisual.BOLD + "> Nome do hábito: " + ConsoleVisual.RESET);
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            ConsoleVisual.error("O nome do hábito não pode ser vazio.");
            return;
        }

        System.out.println("Prioridade:");
        System.out.println("1 - LOW");
        System.out.println("2 - MEDIUM");
        System.out.println("3 - HIGH");
        ConsoleVisual.divider();

        int option = readInt("Escolha a prioridade");

        Priority priority = switch (option) {
            case 1 -> Priority.LOW;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.HIGH;
            default -> null;
        };

        if (priority == null) {
            ConsoleVisual.error("Prioridade inválida.");
            return;
        }

        habitController.createHabit(name, priority);
        ConsoleVisual.success("Hábito criado com sucesso.");
    }

    private void listHabits() {
        ConsoleVisual.printHeader("Lista de Hábitos");

        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        List<Habit> habits = habitController.listHabits();

        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            System.out.printf("%d - %s [%s] [%s]%n",
                    i + 1,
                    h.getName(),
                    h.getPriority(),
                    h.getStatus());
        }

        ConsoleVisual.divider();
        ConsoleVisual.info("Total de hábitos: " + habits.size());
    }

    private void startHabit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        listHabits();
        int index = readInt("Escolha o número do hábito para iniciar") - 1;

        if (habitController.startHabit(index)) {
            ConsoleVisual.success("Hábito iniciado com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }

    private void completeHabit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        listHabits();
        int index = readInt("Escolha o número do hábito para concluir") - 1;

        if (habitController.completeHabit(index)) {
            ConsoleVisual.success("Hábito concluído com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }

    private void editHabit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        listHabits();
        int index = readInt("Escolha o número do hábito para editar") - 1;

        if (index < 0 || index >= habitController.listHabits().size()) {
            ConsoleVisual.error("Índice inválido.");
            return;
        }

        ConsoleVisual.printHeader("Editar Hábito");

        System.out.print(ConsoleVisual.CYAN + ConsoleVisual.BOLD + "> Novo nome (ENTER para manter): " + ConsoleVisual.RESET);
        String newName = scanner.nextLine().trim();

        Habit current = habitController.listHabits().get(index);
        if (newName.isBlank()) {
            newName = current.getName();
        }

        System.out.println("Nova prioridade:");
        System.out.println("1 - LOW");
        System.out.println("2 - MEDIUM");
        System.out.println("3 - HIGH");
        System.out.println("0 - Manter atual (" + current.getPriority() + ")");
        ConsoleVisual.divider();

        int option = readInt("Escolha a prioridade");
        Priority newPriority = switch (option) {
            case 1 -> Priority.LOW;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.HIGH;
            default -> current.getPriority();
        };

        if (habitController.updateHabit(index, newName, newPriority)) {
            ConsoleVisual.success("Hábito atualizado com sucesso.");
        } else {
            ConsoleVisual.error("Erro ao atualizar hábito.");
        }
    }

    private void removeHabit() {
        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Nenhum hábito cadastrado.");
            return;
        }

        listHabits();
        int index = readInt("Escolha o número do hábito para remover") - 1;

        if (habitController.deleteHabit(index)) {
            ConsoleVisual.success("Hábito removido com sucesso.");
        } else {
            ConsoleVisual.error("Índice inválido.");
        }
    }

    private void finishDay() {
        ConsoleVisual.printHeader("Finalizar Dia");

        if (!habitController.hasHabits()) {
            ConsoleVisual.alert("Crie pelo menos um hábito antes de finalizar o dia.");
            return;
        }

        boolean success = habitController.allHighCompleted();

        if (success) {
            userController.registerCompletedDay();
            ConsoleVisual.success("DAY COMPLETE");
        } else {
            userController.registerFailedDay();
            ConsoleVisual.error("DAY FAILED");
        }

        habitController.resetHabits();
        ConsoleVisual.info("Todos os hábitos foram resetados para TODO.");
    }

    private void showStatus() {
        ConsoleVisual.printHeader("Status do Jogador");

        System.out.println("Nome: " + userController.getUser().getName());
        System.out.println("Dias completos: " + userController.getUser().getDaysCompleted());
        System.out.println("Dias falhos: " + userController.getUser().getDaysFailed());
        System.out.println("Streak atual: " + userController.getUser().getCurrentStreak());
        System.out.println("Maior streak: " + userController.getUser().getMaxStreak());
        System.out.println("Level: " + userController.getLevelName());

        ConsoleVisual.divider();

        if (habitController.hasHabits()) {
            System.out.println("Hábitos concluídos hoje: " + habitController.countCompletedHabits());
            System.out.println("Hábitos HIGH: " + habitController.countHabitsByPriority(Priority.HIGH));
            System.out.println("Hábitos MEDIUM: " + habitController.countHabitsByPriority(Priority.MEDIUM));
            System.out.println("Hábitos LOW: " + habitController.countHabitsByPriority(Priority.LOW));
        } else {
            ConsoleVisual.info("Nenhum hábito cadastrado ainda.");
        }
    }

    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(ConsoleVisual.CYAN + ConsoleVisual.BOLD + "> " + message + ": " + ConsoleVisual.RESET);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                ConsoleVisual.error("Digite um número válido.");
            }
        }
    }

    private void pressEnterToContinue() {
        System.out.print(ConsoleVisual.YELLOW + "Pressione ENTER para continuar..." + ConsoleVisual.RESET);
        scanner.nextLine();
    }
}