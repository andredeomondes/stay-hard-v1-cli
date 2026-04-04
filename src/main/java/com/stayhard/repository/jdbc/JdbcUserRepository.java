package com.stayhard.repository.jdbc;

import com.stayhard.domain.entities.User;
import com.stayhard.repository.UserRepository;

import java.sql.*;

public class JdbcUserRepository implements UserRepository {

    @Override
    public User load() {
        String sql = "SELECT name, days_completed, days_failed, current_streak, max_streak FROM users LIMIT 1";

        try (Connection conn = JdbcConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String name = rs.getString("name");
                int daysCompleted = rs.getInt("days_completed");
                int daysFailed = rs.getInt("days_failed");
                int currentStreak = rs.getInt("current_streak");
                int maxStreak = rs.getInt("max_streak");

                return new User(name, daysCompleted, daysFailed, currentStreak, maxStreak);
            }

        } catch (SQLException e) {
            System.err.println("[JdbcUserRepository] Erro ao carregar usuário: " + e.getMessage());
        }

        return new User("Player");
    }

    @Override
    public void save(User user) {
        String sql = """
            UPDATE users SET 
                name = ?,
                days_completed = ?,
                days_failed = ?,
                current_streak = ?,
                max_streak = ?
            WHERE id = 1
            """;

        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.name());
            pstmt.setInt(2, user.daysCompleted());
            pstmt.setInt(3, user.daysFailed());
            pstmt.setInt(4, user.currentStreak());
            pstmt.setInt(5, user.maxStreak());

            int updated = pstmt.executeUpdate();

            if (updated == 0) {
                String insertSql = """
                    INSERT INTO users (name, days_completed, days_failed, current_streak, max_streak)
                    VALUES (?, ?, ?, ?, ?)
                    """;
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, user.name());
                    insertStmt.setInt(2, user.daysCompleted());
                    insertStmt.setInt(3, user.daysFailed());
                    insertStmt.setInt(4, user.currentStreak());
                    insertStmt.setInt(5, user.maxStreak());
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("[JdbcUserRepository] Erro ao salvar usuário: " + e.getMessage());
        }
    }
}
