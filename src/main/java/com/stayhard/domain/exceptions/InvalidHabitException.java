package com.stayhard.domain.exceptions;

public class InvalidHabitException extends RuntimeException {
    
    public InvalidHabitException(String message) {
        super(message);
    }
    
    public InvalidHabitException(String message, Throwable cause) {
        super(message, cause);
    }
}
