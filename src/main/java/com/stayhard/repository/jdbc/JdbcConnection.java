package com.stayhard.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

public class JdbcConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/stayhard";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "admin";

    private final String url;
    private final String user;
    private final String password;

    public JdbcConnection() {
        this(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public JdbcConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void execute(Consumer<Connection> action) {
        try (Connection conn = getConnection()) {
            action.accept(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public static void initializeTables(Connection conn) throws SQLException {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                created_at DATE NOT NULL,
                level INTEGER DEFAULT 1,
                xp INTEGER DEFAULT 0,
                total_habits_completed INTEGER DEFAULT 0
            )
            """;

        String createHabitsTable = """
            CREATE TABLE IF NOT EXISTS habits (
                id BIGSERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                description TEXT,
                priority VARCHAR(20) NOT NULL,
                status VARCHAR(20) NOT NULL,
                created_at DATE NOT NULL,
                completed_at TIMESTAMP,
                streak INTEGER DEFAULT 0,
                user_id BIGINT REFERENCES users(id)
            )
            """;

        try (var stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createHabitsTable);
        }
    }
}
