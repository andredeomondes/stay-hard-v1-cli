package repository.csv;

import domain.entities.Habit;
import domain.enums.Priority;
import domain.enums.Status;
import repository.HabitRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvHabitRepository implements HabitRepository {

    private final Path filePath;

    public CsvHabitRepository(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public List<Habit> load() {
        List<Habit> habits = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return habits;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = reader.readLine(); // header

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;

                String name = parts[0];
                Priority priority = Priority.valueOf(parts[1]);
                Status status = Status.valueOf(parts[2]);

                Habit habit = new Habit(name, priority);
                habit.restoreStatus(status);
                habits.add(habit);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar habits.csv", e);
        }

        return habits;
    }

    @Override
    public void save(List<Habit> habits) {
        try {
            Files.createDirectories(filePath.getParent());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("name,priority,status");
                writer.newLine();

                for (Habit habit : habits) {
                    writer.write(habit.getName() + "," + habit.getPriority() + "," + habit.getStatus());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar habits.csv", e);
        }
    }
}