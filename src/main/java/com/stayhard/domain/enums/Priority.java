package com.stayhard.domain.enums;

public enum Priority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);
    
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
    
    public static Priority fromWeight(int weight) {
        for (Priority p : values()) {
            if (p.weight == weight) {
                return p;
            }
        }
        return null;
    }
}
