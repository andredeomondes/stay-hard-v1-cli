package service;

public class LevelService {

    public String getLevelName(int daysCompleted) {
        if (daysCompleted >= 75) return "Stay Hard";
        if (daysCompleted >= 30) return "Unbreakable";
        if (daysCompleted >= 15) return "Relentless";
        if (daysCompleted >= 7) return "Forged";
        return "Awakening";
    }
}