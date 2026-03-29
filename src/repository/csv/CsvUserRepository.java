package repository.csv;

import domain.entities.User;
import repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvUserRepository implements UserRepository {

    private final Path filePath;
    private final String defaultUserName;

    public CsvUserRepository(String filePath, String defaultUserName) {
        this.filePath = Path.of(filePath);
        this.defaultUserName = defaultUserName;
    }

    @Override
    public User load() {
        if (!Files.exists(filePath)) {
            return new User(defaultUserName);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            reader.readLine(); // header
            String line = reader.readLine();

            if (line == null || line.isBlank()) {
                return new User(defaultUserName);
            }

            String[] parts = line.split(",", -1);

            String name = parts[0];
            int daysCompleted = Integer.parseInt(parts[1]);
            int daysFailed = Integer.parseInt(parts[2]);
            int currentStreak = Integer.parseInt(parts[3]);
            int maxStreak = Integer.parseInt(parts[4]);

            return new User(name, daysCompleted, daysFailed, currentStreak, maxStreak);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar user.csv", e);
        }
    }

    @Override
    public void save(User user) {
        try {
            Files.createDirectories(filePath.getParent());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("name,daysCompleted,daysFailed,currentStreak,maxStreak");
                writer.newLine();
                writer.write(
                        user.getName() + "," +
                                user.getDaysCompleted() + "," +
                                user.getDaysFailed() + "," +
                                user.getCurrentStreak() + "," +
                                user.getMaxStreak()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar user.csv", e);
        }
    }
}