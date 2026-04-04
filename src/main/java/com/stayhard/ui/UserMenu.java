package com.stayhard.ui;

import com.stayhard.domain.entities.User;
import com.stayhard.service.UserService;

public class UserMenu {
    private final UserService userService;
    private final InputReader input;

    public UserMenu(UserService userService) {
        this.userService = userService;
        this.input = new InputReader();
    }

    public void show() {
        String[] options = {
            "Register new user",
            "Login",
            "View profile",
            "Logout",
            "Back to main menu"
        };

        while (true) {
            ConsoleVisual.printHeader("USER MENU");

            if (userService.isLoggedIn()) {
                User user = userService.getCurrentUser();
                System.out.println("Logged in as: " + user.username());
                System.out.println("Level: " + user.level() + " | XP: " + user.xp() + "/100");
            } else {
                ConsoleVisual.printInfo("Not logged in");
            }

            ConsoleVisual.printMenu(options);

            int choice = input.readIntInRange("Choose option: ", 1, options.length);

            try {
                switch (choice) {
                    case 1 -> registerUser();
                    case 2 -> loginUser();
                    case 3 -> viewProfile();
                    case 4 -> logout();
                    case 5 -> { return; }
                }
            } catch (Exception e) {
                ConsoleVisual.printError(e.getMessage());
            }
        }
    }

    private void registerUser() {
        ConsoleVisual.printSubHeader("Register New User");

        String username = input.readLine("Username: ");
        String email = input.readLine("Email: ");

        User user = userService.create(username, email);
        ConsoleVisual.printSuccess("User created: " + user.username());
    }

    private void loginUser() {
        ConsoleVisual.printSubHeader("Login");

        String username = input.readLine("Username: ");

        User user = userService.login(username);
        ConsoleVisual.printSuccess("Welcome back, " + user.username() + "!");
        printProfile(user);
    }

    private void viewProfile() {
        if (!userService.isLoggedIn()) {
            ConsoleVisual.printError("Please login first");
            return;
        }

        printProfile(userService.getCurrentUser());
    }

    private void printProfile(User user) {
        ConsoleVisual.printSubHeader("Profile: " + user.username());

        System.out.println("Email: " + user.email());
        System.out.println("Member since: " + user.createdAt());
        System.out.println("Level: " + user.level());
        System.out.print("XP: ");
        ConsoleVisual.printProgressBar(user.xp(), 100);
        System.out.println("Total habits completed: " + user.totalHabitsCompleted());
    }

    private void logout() {
        if (!userService.isLoggedIn()) {
            ConsoleVisual.printError("Not logged in");
            return;
        }

        if (input.readBoolean("Are you sure you want to logout?")) {
            userService.logout();
            ConsoleVisual.printSuccess("Logged out successfully");
        }
    }
}
