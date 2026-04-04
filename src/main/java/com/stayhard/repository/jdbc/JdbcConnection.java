package com.stayhard.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    private static final String DB_URL = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/stayhard");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "admin");

    private JdbcConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void initializeDatabase() {
        String createHabitsTable = """
            CREATE TABLE IF NOT EXISTS habits (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                priority VARCHAR(20) NOT NULL,
                status VARCHAR(20) NOT NULL
            )
            """;

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                days_completed INT DEFAULT 0,
                days_failed INT DEFAULT 0,
                current_streak INT DEFAULT 0,
                max_streak INT DEFAULT 0
            )
            """;

        String checkHabits = "SELECT COUNT(*) FROM habits";
        String checkUsers = "SELECT COUNT(*) FROM users";

        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {

            stmt.execute(createHabitsTable);
            stmt.execute(createUsersTable);

            var rs = stmt.executeQuery(checkHabits);
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("[JDBC] Tabela habits inicializada.");
            }

            rs = stmt.executeQuery(checkUsers);
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO users (name, days_completed, days_failed, current_streak, max_streak) VALUES ('Player', 0, 0, 0, 0)");
                System.out.println("[JDBC] Tabela users inicializada com usuário padrão.");
            }

        } catch (SQLException e) {
            System.err.println("[JDBC] Erro ao inicializar banco: " + e.getMessage());
            System.err.println("[JDBC] Verifique se o PostgreSQL está rodando e o banco 'stayhard' existe.");
            System.err.println("[JDBC] Para criar o banco: CREATE DATABASE stayhard;");
        }
    }
}
