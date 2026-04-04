package com.stayhard.ui;

import com.stayhard.controller.UserController;
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
        return userController.getUser().currentStreak();
    }

    public String getLevelName() {
        return userController.getLevelName();
    }

    public void showStatus() {
        ConsoleVisual.printHeader("Status do Jogador");

        System.out.println("Nome: " + userController.getUser().name());
        System.out.println("Dias completos: " + userController.getUser().daysCompleted());
        System.out.println("Dias falhos: " + userController.getUser().daysFailed());
        System.out.println("Streak atual: " + userController.getUser().currentStreak());
        System.out.println("Maior streak: " + userController.getUser().maxStreak());
        System.out.println("Level: " + userController.getLevelName());

        ConsoleVisual.divider();

        if (hasHabits) {
            ConsoleVisual.info("Consulte os hábitos para ver detalhes.");
        } else {
            ConsoleVisual.info("Nenhum hábito cadastrado ainda.");
        }
    }
}
