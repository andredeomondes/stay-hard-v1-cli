package com.stayhard.ui;

import com.stayhard.controller.UserController;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.utils.ConsoleVisual;

public class UserMenu {

    private final UserController userController;
    private final boolean hasHabits;
    private final InputReader input;

    public UserMenu(UserController userController, boolean hasHabits, InputReader input) {
        this.userController = userController;
        this.hasHabits = hasHabits;
        this.input = input;
    }

    public int getCurrentDay() {
        return userController.getCurrentDay();
    }

    public int getCurrentStreak() {
        return userController.getUser().getCurrentStreak();
    }

    public String getLevelName() {
        return userController.getLevelName();
    }

    public void showStatus() {
        ConsoleVisual.printHeader("Status do Jogador");

        System.out.println("Nome: " + userController.getUser().getName());
        System.out.println("Dias completos: " + userController.getUser().getDaysCompleted());
        System.out.println("Dias falhos: " + userController.getUser().getDaysFailed());
        System.out.println("Streak atual: " + userController.getUser().getCurrentStreak());
        System.out.println("Maior streak: " + userController.getUser().getMaxStreak());
        System.out.println("Level: " + userController.getLevelName());

        ConsoleVisual.divider();

        if (hasHabits) {
            System.out.println("Hábitos concluídos hoje: " + userController.getCompletedToday());
            System.out.println("Hábitos HIGH: " + userController.getHabitsByPriority(Priority.HIGH));
            System.out.println("Hábitos MEDIUM: " + userController.getHabitsByPriority(Priority.MEDIUM));
            System.out.println("Hábitos LOW: " + userController.getHabitsByPriority(Priority.LOW));
        } else {
            ConsoleVisual.info("Nenhum hábito cadastrado ainda.");
        }
    }

    public void finishDay(boolean allHighCompleted) {
        ConsoleVisual.printHeader("Finalizar Dia");

        if (!hasHabits) {
            ConsoleVisual.alert("Crie pelo menos um hábito antes de finalizar o dia.");
            return;
        }

        if (allHighCompleted) {
            userController.registerCompletedDay();
            ConsoleVisual.success("DAY COMPLETE");
        } else {
            userController.registerFailedDay();
            ConsoleVisual.error("DAY FAILED");
        }

        ConsoleVisual.info("Todos os hábitos foram resetados para TODO.");
    }
}
