package com.stayhard.controller;

import com.stayhard.domain.entities.User;
import com.stayhard.domain.enums.Priority;
import com.stayhard.service.LevelService;
import com.stayhard.service.UserService;

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
        return levelService.getLevelName(getUser().daysCompleted());
    }

    public int getCurrentDay() {
        User user = getUser();
        return user.daysCompleted() + user.daysFailed() + 1;
    }

    public long getCompletedToday() {
        return 0;
    }

    public long getHabitsByPriority(Priority priority) {
        return 0;
    }
}
