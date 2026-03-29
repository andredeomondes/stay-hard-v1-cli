package domain.entities;

public class User {

    private String name;
    private int daysCompleted;
    private int daysFailed;
    private int currentStreak;
    private int maxStreak;

    public User(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        this.name = name;
        this.daysCompleted = 0;
        this.daysFailed = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
    }

    public User(String name, int daysCompleted, int daysFailed, int currentStreak, int maxStreak) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        this.name = name;
        this.daysCompleted = daysCompleted;
        this.daysFailed = daysFailed;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
    }

    public String getName() {
        return name;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public int getDaysFailed() {
        return daysFailed;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void addCompletedDay() {
        this.daysCompleted++;
        this.currentStreak++;

        this.maxStreak = Math.max(this.maxStreak, this.currentStreak);
    }

    public void addFailedDay() {
        this.daysFailed++;
        this.currentStreak = 0;
    }
}