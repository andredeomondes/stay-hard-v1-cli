package com.stayhard.domain.enums;

public enum Priority {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int weight;

    Priority(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isHigherThan(Priority other) {
        return this.weight > other.weight;
    }

    public boolean isLowerThan(Priority other) {
        return this.weight < other.weight;
    }

    public static Priority fromString(String value) {
        try {
            return Priority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority: " + value);
        }
    }
}
