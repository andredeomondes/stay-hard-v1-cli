package com.stayhard.ui;

import com.stayhard.repository.jdbc.JdbcConnection;
import com.stayhard.repository.jdbc.JdbcHabitRepository;
import com.stayhard.repository.jdbc.JdbcUserRepository;
import com.stayhard.service.HabitService;
import com.stayhard.service.UserService;

public class UserMenus {
    private final HabitService habitService;
    private final UserService userService;
    private final HabitMenu habitMenu;
    private final UserMenu userMenu;
    private final InputReader input;

    public UserMenus() {
        JdbcConnection jdbcConnection = new JdbcConnection();
        JdbcHabitRepository habitRepository = new JdbcHabitRepository(jdbcConnection);
        JdbcUserRepository userRepository = new JdbcUserRepository(jdbcConnection);

        this.habitService = new HabitService(habitRepository);
        this.userService = new UserService(userRepository);
        this.habitMenu = new HabitMenu(habitService);
        this.userMenu = new UserMenu(userService);
        this.input = new InputReader();

        habitMenu.setCurrentUserId(null);
    }

    public void showMainMenu() {
        String[] options = {
            "Habit Management",
            "User Management",
            "Exit"
        };

        while (true) {
            ConsoleVisual.printBanner();
            ConsoleVisual.printHeader("MAIN MENU");

            if (userService.isLoggedIn()) {
                System.out.println("User: " + userService.getCurrentUser().username() +
                    " | Level: " + userService.getCurrentUser().level());
            }

            ConsoleVisual.printMenu(options);

            int choice = input.readIntInRange("Choose option: ", 1, options.length);

            switch (choice) {
                case 1 -> {
                    if (userService.isLoggedIn()) {
                        habitMenu.setCurrentUserId(userService.getCurrentUser().id());
                    }
                    habitMenu.show();
                }
                case 2 -> userMenu.show();
                case 3 -> {
                    ConsoleVisual.printInfo("Stay hard! Goodbye!");
                    return;
                }
            }
        }
    }
}
