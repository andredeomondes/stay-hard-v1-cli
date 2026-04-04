package com.stayhard.domain.exceptions;

public class HabitNotFoundException extends RuntimeException {
    
    public HabitNotFoundException(int index) {
        super("Hábito não encontrado no índice: " + index);
    }
    
    public HabitNotFoundException(String message) {
        super(message);
    }
}
