package com.stayhard.ui;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.service.HabitService;

import java.util.List;

public class HabitMenu {
    private final HabitService habitService;
    private final InputReader input;
    private Long currentUserId;
    private boolean setupComplete = false;

    public HabitMenu(HabitService habitService) {
        this.habitService = habitService;
        this.input = new InputReader();
    }

    public void setCurrentUserId(Long userId) {
        this.currentUserId = userId;
        this.setupComplete = false;
    }

    public void show() {
        List<Habit> existingHabits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        if (existingHabits.isEmpty() && !setupComplete) {
            setupInitialHabits();
        }

        showMainMenu();
    }

    private void setupInitialHabits() {
        ConsoleVisual.printHeader("SETUP INICIAL");
        System.out.println();
        System.out.println("Bem-vindo ao Stay Hard!");
        System.out.println("Você precisa adicionar no mínimo 4 hábitos para começar.");
        System.out.println("Esses são os hábitos que você vai manter todos os dias.");
        System.out.println();

        int count = 0;
        while (count < 4) {
            ConsoleVisual.printSubHeader("Hábitos: " + count + "/4 (mínimo)");
            System.out.println("Hábitos restantes: " + (4 - count));

            String name = input.readLine("Nome do hábito: ");
            String description = input.readLine("Descrição (opcional): ");
            int priorityChoice = input.readIntInRange("Prioridade (1=LOW, 2=MEDIUM, 3=HIGH, 4=CRITICAL): ", 1, 4);

            Priority priority = Priority.values()[priorityChoice - 1];
            habitService.create(name, description, priority, currentUserId);
            count++;

            if (count < 4) {
                ConsoleVisual.printSuccess("Hábito adicionado! Continue...");
            }
        }

        ConsoleVisual.printSuccess("Parabéns! Você definiu seus 4 hábitos.");
        ConsoleVisual.printInfo("Agora complete-os um por um e mantenha os streaks!");
        setupComplete = true;
    }

    private void showMainMenu() {
        String[] options = {
            "Listar hábitos",
            "Completar hábito",
            "Marcar incompleto",
            "Atualizar hábito",
            "Estatísticas",
            "Voltar ao menu principal"
        };

        while (true) {
            ConsoleVisual.printHeader("HABITS");
            printAllHabits();
            System.out.println();
            ConsoleVisual.printMenu(options);

            int choice = input.readIntInRange("Escolha: ", 1, options.length);

            try {
                switch (choice) {
                    case 1 -> listHabits();
                    case 2 -> completeHabit();
                    case 3 -> markIncomplete();
                    case 4 -> updateHabit();
                    case 5 -> showStatistics();
                    case 6 -> { return; }
                }
            } catch (Exception e) {
                ConsoleVisual.printError(e.getMessage());
            }
        }
    }

    private void printAllHabits() {
        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        if (habits.isEmpty()) {
            ConsoleVisual.printInfo("Nenhum hábito encontrado.");
            return;
        }

        System.out.println();
        for (Habit habit : habits) {
            printHabit(habit);
        }

        int completed = (int) habits.stream().filter(h -> h.status() == Status.COMPLETED).count();
        System.out.println();
        System.out.printf("Progresso: %d/%d completados%n", completed, habits.size());
    }

    private void printHabit(Habit habit) {
        String statusIcon = habit.status() == Status.COMPLETED ? "✅" : "⬜";
        String priorityStr = habit.priority().name();
        String streakStr = habit.streak() > 0 ? "🔥 x" + habit.streak() : "";
        String highStreak = habit.streak() >= 3 ? " ⭐" : "";

        System.out.printf("  %s [%s] %-25s %s%s%n",
            statusIcon,
            priorityStr.substring(0, 3).toUpperCase(),
            habit.name(),
            streakStr,
            highStreak);
    }

    private void listHabits() {
        ConsoleVisual.printSubHeader("Seus Hábitos");
        printAllHabits();
    }

    private void completeHabit() {
        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        System.out.println();
        System.out.println("Escolha o hábito para completar:");
        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            if (h.status() != Status.COMPLETED) {
                System.out.printf("  %d. %s%n", i + 1, h.name());
            }
        }
        System.out.println("  0. Voltar");

        int choice = input.readIntInRange("Escolha: ", 0, habits.size());
        if (choice == 0) return;

        Habit selected = habits.get(choice - 1);
        Habit completed = habitService.complete(selected.id());
        ConsoleVisual.printSuccess("✅ Completado: " + completed.name());
        System.out.println("   Streak: " + completed.streak() + " dias!");
    }

    private void markIncomplete() {
        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        System.out.println();
        System.out.println("Escolha o hábito para resetar:");
        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            System.out.printf("  %d. %s%n", i + 1, h.name());
        }
        System.out.println("  0. Voltar");

        int choice = input.readIntInRange("Escolha: ", 0, habits.size());
        if (choice == 0) return;

        Habit selected = habits.get(choice - 1);
        Habit incomplete = habitService.incomplete(selected.id());
        ConsoleVisual.printSuccess("Streak resetado para: " + incomplete.name());
    }

    private void updateHabit() {
        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        System.out.println();
        System.out.println("Escolha o hábito para atualizar:");
        for (int i = 0; i < habits.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, habits.get(i).name());
        }
        System.out.println("  0. Voltar");

        int choice = input.readIntInRange("Escolha: ", 0, habits.size());
        if (choice == 0) return;

        Habit selected = habits.get(choice - 1);
        System.out.println("Atualizando: " + selected.name());

        String name = input.readLine("Novo nome (Enter para pular): ");
        String description = input.readLine("Nova descrição (Enter para pular): ");
        String priorityStr = input.readLine("Nova prioridade (1-4, Enter para pular): ");

        Priority priority = null;
        if (!priorityStr.isBlank()) {
            int p = Integer.parseInt(priorityStr);
            priority = Priority.values()[p - 1];
        }

        Habit updated = habitService.update(
            selected.id(),
            name.isBlank() ? null : name,
            description.isBlank() ? null : description,
            priority
        );
        ConsoleVisual.printSuccess("Atualizado: " + updated.name());
    }

    private void showStatistics() {
        ConsoleVisual.printSubHeader("Estatísticas");

        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        int total = habits.size();
        int completed = (int) habits.stream().filter(h -> h.status() == Status.COMPLETED).count();
        double rate = total > 0 ? (double) completed / total * 100 : 0;

        int totalStreak = habits.stream().mapToInt(Habit::streak).sum();
        int maxStreak = habits.stream().mapToInt(Habit::streak).max().orElse(0);

        System.out.println();
        System.out.println("📊 Resumo do dia:");
        System.out.println("   Total de hábitos: " + total);
        System.out.println("   Completados: " + completed);
        System.out.printf("   Taxa de Completion: %.0f%%%n", rate);
        System.out.println();
        System.out.println("🔥 Streaks:");
        System.out.println("   Soma total: " + totalStreak + " dias");
        System.out.println("   Maior streak: " + maxStreak + " dias");

        System.out.println();
        ConsoleVisual.printProgressBar(completed, total);

        if (completed == total && total > 0) {
            ConsoleVisual.printSuccess("Parabéns! Você completou todos os hábitos hoje!");
        }
    }
}
