package com.stayhard.repository.jdbc;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.repository.HabitRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHabitRepository implements HabitRepository {

    @Override
    public List<Habit> load() {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT name, priority, status FROM habits ORDER BY id";

        try (Connection conn = JdbcConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                Priority priority = Priority.valueOf(rs.getString("priority"));
                Status status = Status.valueOf(rs.getString("status"));
                habits.add(new Habit(name, priority, status));
            }

        } catch (SQLException e) {
            System.err.println("[JdbcHabitRepository] Erro ao carregar hábitos: " + e.getMessage());
        }

        return habits;
    }

    @Override
    public void save(List<Habit> habits) {
        String deleteSql = "DELETE FROM habits";
        String insertSql = "INSERT INTO habits (name, priority, status) VALUES (?, ?, ?)";

        try (Connection conn = JdbcConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(deleteSql);
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                for (Habit habit : habits) {
                    pstmt.setString(1, habit.name());
                    pstmt.setString(2, habit.priority().name());
                    pstmt.setString(3, habit.status().name());
                    pstmt.executeUpdate();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("[JdbcHabitRepository] Erro ao salvar hábitos: " + e.getMessage());
            try {
                Connection conn = JdbcConnection.getConnection();
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("[JdbcHabitRepository] Erro no rollback: " + ex.getMessage());
            }
        }
    }
}
