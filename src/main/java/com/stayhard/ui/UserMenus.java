package com.stayhard.ui;

import com.stayhard.controller.HabitController;
import com.stayhard.controller.UserController;
import com.stayhard.domain.utils.ConsoleVisual;

public class UserMenus {

    private final HabitController habitController;
    private final UserMenu userMenu;
    private final HabitMenu habitMenu;
    private final InputReader input;

    public UserMenus(HabitController habitController, UserController userController) {
        this.habitController = habitController;
        this.input = new InputReader();
        this.userMenu = new UserMenu(userController, habitController.hasHabits(), input);
        this.habitMenu = new HabitMenu(habitController, input);
    }

    public void start() {
        int option;

        do {
            showMainMenu();
            option = input.readInt("Escolha uma opção");

            switch (option) {
                case 1 -> habitMenu.create();
                case 2 -> habitMenu.list();
                case 3 -> habitMenu.start();
                case 4 -> habitMenu.complete();
                case 5 -> habitMenu.edit();
                case 6 -> habitMenu.remove();
                case 7 -> habitMenu.filter();
                case 8 -> userMenu.showStatus();
                case 9 -> {
                    habitController.finishDay();
                }
                case 0 -> ConsoleVisual.info("Saindo do sistema...");
                default -> ConsoleVisual.error("Opção inválida.");
            }

            if (option != 0) {
                input.pressEnterToContinue();
            }

        } while (option != 0);
    }

    private void showMainMenu() {
        ConsoleVisual.printHeader("Stay Hard System");

        System.out.println("Day: " + userMenu.getCurrentDay());
        System.out.println("Streak: " + userMenu.getCurrentStreak());
        System.out.println("Level: " + userMenu.getLevelName());

        ConsoleVisual.divider();

        System.out.println("1 - Criar hábito");
        System.out.println("2 - Listar hábitos");
        System.out.println("3 - Iniciar hábito");
        System.out.println("4 - Concluir hábito");
        System.out.println("5 - Editar hábito");
        System.out.println("6 - Remover hábito");
        System.out.println("7 - Filtrar hábitos");
        System.out.println("8 - Ver status");
        System.out.println("9 - Finalizar dia");
        System.out.println("0 - Sair");

        ConsoleVisual.divider();
    }
}
