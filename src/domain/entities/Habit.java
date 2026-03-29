package domain.entities;

import domain.enums.Priority;
import domain.enums.Status;

public class Habit {
    private String name;
    private Priority priority;
    private Status status;
    public Habit(String name, Priority priority) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do hábito não pode ser vazio");
        }

        if (priority == null) {
            throw new IllegalArgumentException("Prioridade não pode ser nula");
        }

        this.name = name;
        this.priority = priority;
        this.status = Status.TODO ;



    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void start() {
        if (status == Status.TODO) {
            status = Status.IN_PROGRESS;
        }
    }

    public void complete() {
        status = Status.DONE;
    }

    public void reset() {
        status = Status.TODO;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do hábito não pode ser vazio");
        }
        this.name = name;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Prioridade não pode ser nula");
        }
        this.priority = priority;
    }

    public void restoreStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo.");
        }
        this.status = status;
    }

}
