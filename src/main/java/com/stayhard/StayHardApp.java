package com.stayhard;

import com.stayhard.controller.HabitController;
import com.stayhard.controller.UserController;
import com.stayhard.service.HabitService;
import com.stayhard.service.LevelService;
import com.stayhard.service.UserService;
import com.stayhard.ui.UserMenus;
import com.stayhard.repository.HabitRepository;
import com.stayhard.repository.UserRepository;
import com.stayhard.repository.csv.CsvHabitRepository;
import com.stayhard.repository.csv.CsvUserRepository;

public class StayHardApp {
    public static void main(String[] args) {

        HabitRepository habitRepository = new CsvHabitRepository("data/habits.csv");
        UserRepository userRepository = new CsvUserRepository("data/user.csv", "Player");

        HabitService habitService = new HabitService(habitRepository);
        UserService userService = new UserService(userRepository);
        LevelService levelService = new LevelService();

        HabitController habitController = new HabitController(habitService);
        UserController userController = new UserController(userService, levelService);

        UserMenus menu = new UserMenus(habitController, userController);
        menu.start();
    }
}