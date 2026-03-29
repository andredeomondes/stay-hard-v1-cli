package controller;

import domain.entities.User;
import service.LevelService;
import service.UserService;

public class UserController {

    private final UserService userService;
    private final LevelService levelService;

    public UserController(UserService userService, LevelService levelService) {
        this.userService = userService;
        this.levelService = levelService;
    }

    public User getUser() {
        return userService.getUser();
    }

    public void registerCompletedDay() {
        userService.registerCompletedDay();
    }

    public void registerFailedDay() {
        userService.registerFailedDay();
    }

    public String getLevelName() {
        return levelService.getLevelName(getUser().getDaysCompleted());
    }

    public int getCurrentDay() {
        User user = getUser();
        return user.getDaysCompleted() + user.getDaysFailed() + 1;
    }
}