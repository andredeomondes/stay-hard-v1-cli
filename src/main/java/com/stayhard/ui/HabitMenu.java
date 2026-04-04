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

    public HabitMenu(HabitService habitService) {
        this.habitService = habitService;
        this.input = new InputReader();
    }

    public void setCurrentUserId(Long userId) {
        this.currentUserId = userId;
    }

    public void show() {
        String[] options = {
            "List all habits",
            "Create new habit",
            "Complete a habit",
            "Mark habit incomplete",
            "Update habit",
            "Delete habit",
            "Filter by priority",
            "Filter by status",
            "Statistics",
            "Back to main menu"
        };

        while (true) {
            ConsoleVisual.printHeader("HABITS");
            ConsoleVisual.printMenu(options);

            int choice = input.readIntInRange("Choose option: ", 1, options.length);

            try {
                switch (choice) {
                    case 1 -> listHabits();
                    case 2 -> createHabit();
                    case 3 -> completeHabit();
                    case 4 -> markIncomplete();
                    case 5 -> updateHabit();
                    case 6 -> deleteHabit();
                    case 7 -> filterByPriority();
                    case 8 -> filterByStatus();
                    case 9 -> showStatistics();
                    case 10 -> { return; }
                }
            } catch (Exception e) {
                ConsoleVisual.printError(e.getMessage());
            }
        }
    }

    private void listHabits() {
        ConsoleVisual.printSubHeader("Your Habits");

        List<Habit> habits = currentUserId != null
            ? habitService.findByUserId(currentUserId)
            : habitService.findAll();

        if (habits.isEmpty()) {
            ConsoleVisual.printInfo("No habits found. Create one!");
            return;
        }

        List<Habit> sorted = habitService.getSortedByPriority();
        for (Habit habit : sorted) {
            printHabit(habit);
        }
    }

    private void printHabit(Habit habit) {
        String statusIcon = habit.status() == Status.COMPLETED ? "✅" : "⬜";
        String priorityBadge = "[" + habit.priority().name() + "]";
        String streak = habit.streak() > 0 ? "🔥 x" + habit.streak() : "";

        System.out.printf("%s %-30s %s %s%n",
            statusIcon,
            habit.name(),
            priorityBadge,
            streak);
    }

    private void createHabit() {
        ConsoleVisual.printSubHeader("Create New Habit");

        String name = input.readLine("Habit name: ");
        String description = input.readLine("Description (optional): ");
        int priorityChoice = input.readIntInRange("Priority (1=LOW, 2=MEDIUM, 3=HIGH, 4=CRITICAL): ", 1, 4);

        Priority priority = Priority.values()[priorityChoice - 1];

        Habit habit = habitService.create(name, description, priority, currentUserId);
        ConsoleVisual.printSuccess("Habit created: " + habit.name());
    }

    private void completeHabit() {
        listHabits();
        int id = input.readInt("Enter habit ID to complete: ");
        Habit habit = habitService.complete((long) id);
        ConsoleVisual.printSuccess("Completed: " + habit.name());
    }

    private void markIncomplete() {
        listHabits();
        int id = input.readInt("Enter habit ID to mark incomplete: ");
        Habit habit = habitService.incomplete((long) id);
        ConsoleVisual.printSuccess("Reset: " + habit.name());
    }

    private void updateHabit() {
        listHabits();
        int id = input.readInt("Enter habit ID to update: ");

        String name = input.readLine("New name (press Enter to skip): ");
        String description = input.readLine("New description (press Enter to skip): ");
        String priorityStr = input.readLine("New priority (1=LOW, 2=MEDIUM, 3=HIGH, 4=CRITICAL, Enter to skip): ");

        Priority priority = null;
        if (!priorityStr.isBlank()) {
            int p = Integer.parseInt(priorityStr);
            priority = Priority.values()[p - 1];
        }

        Habit habit = habitService.update(
            (long) id,
            name.isBlank() ? null : name,
            description.isBlank() ? null : description,
            priority
        );
        ConsoleVisual.printSuccess("Updated: " + habit.name());
    }

    private void deleteHabit() {
        listHabits();
        int id = input.readInt("Enter habit ID to delete: ");
        if (input.readBoolean("Are you sure?")) {
            habitService.delete((long) id);
            ConsoleVisual.printSuccess("Habit deleted");
        }
    }

    private void filterByPriority() {
        ConsoleVisual.printSubHeader("Filter by Priority");
        int choice = input.readIntInRange("1=LOW, 2=MEDIUM, 3=HIGH, 4=CRITICAL: ", 1, 4);
        Priority priority = Priority.values()[choice - 1];

        List<Habit> habits = habitService.filterByPriority(priority);
        ConsoleVisual.printInfo("Found " + habits.size() + " habits with priority " + priority);
        habits.forEach(this::printHabit);
    }

    private void filterByStatus() {
        ConsoleVisual.printSubHeader("Filter by Status");
        int choice = input.readIntInRange("1=PENDING, 2=IN_PROGRESS, 3=COMPLETED, 4=CANCELLED: ", 1, 4);
        Status status = Status.values()[choice - 1];

        List<Habit> habits = habitService.filterByStatus(status);
        ConsoleVisual.printInfo("Found " + habits.size() + " habits with status " + status);
        habits.forEach(this::printHabit);
    }

    private void showStatistics() {
        ConsoleVisual.printSubHeader("Statistics");

        int total = habitService.getTotalCount();
        int completed = habitService.getCompletedCount();
        double rate = habitService.getCompletionRate();

        System.out.printf("Total habits: %d%n", total);
        System.out.printf("Completed: %d%n", completed);
        System.out.printf("Completion rate: %.1f%%%n", rate);

        List<Habit> highPriority = habitService.getHighPriorityCompleted();
        System.out.printf("High priority completed: %d%n", highPriority.size());

        ConsoleVisual.printProgressBar(completed, total);
    }
}
