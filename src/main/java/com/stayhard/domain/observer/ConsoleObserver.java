package com.stayhard.domain.observer;

import java.time.format.DateTimeFormatter;

public class ConsoleObserver implements HabitObserver {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void onHabitEvent(HabitEvent event) {
        String timestamp = event.timestamp().format(FORMATTER);
        String emoji = getEmoji(event.eventType());
        String habitName = event.habit() != null ? event.habit().name() : "Unknown";

        System.out.printf("[%s] %s %s: %s%n",
            timestamp,
            emoji,
            event.eventType(),
            habitName);
    }

    private String getEmoji(String eventType) {
        return switch (eventType) {
            case "CREATED" -> "➕";
            case "COMPLETED" -> "✅";
            case "UPDATED" -> "✏️";
            case "DELETED" -> "🗑️";
            default -> "📝";
        };
    }

    @Override
    public String getObserverName() {
        return "ConsoleObserver";
    }
}
