package com.stayhard;

import com.stayhard.controller.HabitController;
import com.stayhard.controller.UserController;
import com.stayhard.domain.observer.ConsoleObserver;
import com.stayhard.repository.HabitRepository;
import com.stayhard.repository.UserRepository;
import com.stayhard.repository.jdbc.JdbcConnection;
import com.stayhard.repository.jdbc.JdbcHabitRepository;
import com.stayhard.repository.jdbc.JdbcUserRepository;
import com.stayhard.service.HabitService;
import com.stayhard.service.LevelService;
import com.stayhard.service.UserService;
import com.stayhard.ui.UserMenus;

public class StayHardApp {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       STAY HARD SYSTEM - JDBC");
        System.out.println("========================================\n");

        JdbcConnection.initializeDatabase();

        HabitRepository habitRepository = new JdbcHabitRepository();
        UserRepository userRepository = new JdbcUserRepository();

        HabitService habitService = new HabitService(habitRepository);
        UserService userService = new UserService(userRepository);
        LevelService levelService = new LevelService();

        habitService.addObserver(new ConsoleObserver());

        HabitController habitController = new HabitController(habitService);
        UserController userController = new UserController(userService, levelService);

        UserMenus menu = new UserMenus(habitController, userController);
        menu.start();
    }
}
