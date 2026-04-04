package com.stayhard.domain.enums;

public enum Status {
    TODO(false),
    IN_PROGRESS(true),
    DONE(true);
    
    private final boolean inProgress;
    
    Status(boolean inProgress) {
        this.inProgress = inProgress;
    }
    
    public boolean isInProgress() {
        return inProgress;
    }
    
    public boolean isCompleted() {
        return this == DONE;
    }
    
    public boolean canTransitionTo(Status newStatus) {
        return switch (this) {
            case TODO -> newStatus == IN_PROGRESS || newStatus == DONE;
            case IN_PROGRESS -> newStatus == DONE || newStatus == TODO;
            case DONE -> newStatus == TODO;
        };
    }
}
