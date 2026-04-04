package com.stayhard.repository.jdbc;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.domain.exceptions.RepositoryException;
import com.stayhard.repository.HabitRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcHabitRepository implements HabitRepository {
    private final JdbcConnection jdbcConnection;

    public JdbcHabitRepository(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    @Override
    public Habit save(Habit habit) {
        String sql = """
            INSERT INTO habits (name, description, priority, status, created_at, completed_at, streak, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, habit.name());
            stmt.setString(2, habit.description());
            stmt.setString(3, habit.priority().name());
            stmt.setString(4, habit.status().name());
            stmt.setDate(5, Date.valueOf(habit.createdAt()));
            stmt.setTimestamp(6, habit.completedAt() != null ? Timestamp.valueOf(habit.completedAt()) : null);
            stmt.setInt(7, habit.streak());
            stmt.setObject(8, habit.userId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return habit.withId(rs.getLong(1));
                }
            }
            return habit;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save habit", e);
        }
    }

    @Override
    public Optional<Habit> findById(Long id) {
        String sql = "SELECT * FROM habits WHERE id = ?";

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToHabit(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find habit by id", e);
        }
    }

    @Override
    public List<Habit> findAll() {
        String sql = "SELECT * FROM habits ORDER BY created_at DESC";
        List<Habit> habits = new ArrayList<>();

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                habits.add(mapResultSetToHabit(rs));
            }
            return habits;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find all habits", e);
        }
    }

    @Override
    public List<Habit> findByUserId(Long userId) {
        String sql = "SELECT * FROM habits WHERE user_id = ? ORDER BY created_at DESC";
        List<Habit> habits = new ArrayList<>();

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    habits.add(mapResultSetToHabit(rs));
                }
            }
            return habits;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find habits by user id", e);
        }
    }

    @Override
    public Habit update(Habit habit) {
        String sql = """
            UPDATE habits
            SET name = ?, description = ?, priority = ?, status = ?,
                completed_at = ?, streak = ?, user_id = ?
            WHERE id = ?
            """;

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, habit.name());
            stmt.setString(2, habit.description());
            stmt.setString(3, habit.priority().name());
            stmt.setString(4, habit.status().name());
            stmt.setTimestamp(5, habit.completedAt() != null ? Timestamp.valueOf(habit.completedAt()) : null);
            stmt.setInt(6, habit.streak());
            stmt.setObject(7, habit.userId());
            stmt.setLong(8, habit.id());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RepositoryException("Habit not found with id: " + habit.id());
            }
            return habit;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update habit", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM habits WHERE id = ?";

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to delete habit", e);
        }
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM habits";

        try (Connection conn = jdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to count habits", e);
        }
    }

    private Habit mapResultSetToHabit(ResultSet rs) throws SQLException {
        return new Habit(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            Priority.valueOf(rs.getString("priority")),
            Status.valueOf(rs.getString("status")),
            rs.getDate("created_at").toLocalDate(),
            rs.getTimestamp("completed_at") != null ? rs.getTimestamp("completed_at").toLocalDateTime() : null,
            rs.getInt("streak"),
            rs.getObject("user_id", Long.class)
        );
    }
}
