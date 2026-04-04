package com.stayhard.domain.enums;

import java.util.Set;

public enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public boolean isInProgress() {
        return this == IN_PROGRESS || this == PENDING;
    }

    public boolean canTransitionTo(Status newStatus) {
        return switch (this) {
            case PENDING -> newStatus == IN_PROGRESS || newStatus == COMPLETED;
            case IN_PROGRESS -> newStatus == COMPLETED || newStatus == CANCELLED;
            case COMPLETED -> newStatus == PENDING;
            case CANCELLED -> newStatus == PENDING;
        };
    }

    public Set<Status> allowedTransitions() {
        return switch (this) {
            case PENDING -> Set.of(IN_PROGRESS, COMPLETED);
            case IN_PROGRESS -> Set.of(COMPLETED, CANCELLED);
            case COMPLETED -> Set.of(PENDING);
            case CANCELLED -> Set.of(PENDING);
        };
    }

    public static Status fromString(String value) {
        try {
            return Status.valueOf(value.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + value);
        }
    }
}
