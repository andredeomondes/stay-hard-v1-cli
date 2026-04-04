package com.stayhard;

import com.stayhard.repository.jdbc.JdbcConnection;
import com.stayhard.ui.ConsoleVisual;
import com.stayhard.ui.UserMenus;

public class StayHardApp {
    public static void main(String[] args) {
        try {
            ConsoleVisual.printBanner();

            JdbcConnection jdbcConnection = new JdbcConnection();
            try (var conn = jdbcConnection.getConnection()) {
                ConsoleVisual.printSuccess("Connected to PostgreSQL database!");
                JdbcConnection.initializeTables(conn);
                ConsoleVisual.printSuccess("Database tables initialized!");
            }

            UserMenus menus = new UserMenus();
            menus.showMainMenu();

        } catch (Exception e) {
            ConsoleVisual.printError("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
