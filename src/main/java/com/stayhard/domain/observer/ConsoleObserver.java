package com.stayhard.domain.observer;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.utils.ConsoleVisual;

public class ConsoleObserver implements HabitObserver {

    @Override
    public void onHabitCompleted(Habit habit) {
        ConsoleVisual.success("Hábito concluído: " + habit.name());
    }

    @Override
    public void onHabitStarted(Habit habit) {
        ConsoleVisual.info("Hábito iniciado: " + habit.name());
    }

    @Override
    public void onHabitDeleted(Habit habit) {
        ConsoleVisual.alert("Hábito removido: " + habit.name());
    }

    @Override
    public void onHabitAdded(Habit habit) {
        ConsoleVisual.success("Novo hábito criado: " + habit.name());
    }

    @Override
    public void onDayFinished(boolean allCompleted, int completedCount, int totalCount) {
        if (allCompleted) {
            ConsoleVisual.success("Parabéns! Todos os hábitos HIGH foram concluídos!");
        } else {
            ConsoleVisual.error("Dia encerrado. " + completedCount + "/" + totalCount + " hábitos concluídos.");
        }
    }
}
