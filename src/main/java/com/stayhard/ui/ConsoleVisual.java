package com.stayhard.ui;

public class ConsoleVisual {
    private static final String BANNER = """
        
        ╔═══════════════════════════════════════════╗
        ║                                           ║
        ║   ███████╗███████╗ ██████╗ █████╗ ██████╗ ║
        ║   ██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗║
        ║   █████╗  ███████╗██║     ███████║██████╔╝║
        ║   ██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ║
        ║   ███████╗███████║╚██████╗██║  ██║██║     ║
        ║   ╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ║
        ║                                           ║
        ║         Stay Hard. Never Settle.          ║
        ║                                           ║
        ╚═══════════════════════════════════════════╝
        """;

    public static void printBanner() {
        System.out.println(BANNER);
    }

    public static void printHeader(String title) {
        int padding = (40 - title.length()) / 2;
        String padded = " ".repeat(Math.max(0, padding));
        System.out.println("\n╔" + "═".repeat(40) + "╗");
        System.out.println("║" + padded + title + padded + "║");
        System.out.println("╚" + "═".repeat(40) + "╝");
    }

    public static void printSubHeader(String title) {
        System.out.println("\n── " + title + " ──");
    }

    public static void printSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public static void printError(String message) {
        System.out.println("❌ " + message);
    }

    public static void printInfo(String message) {
        System.out.println("ℹ️  " + message);
    }

    public static void printMenu(String[] options) {
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, options[i]);
        }
        System.out.println();
    }

    public static void printProgressBar(int current, int total) {
        int width = 30;
        double progress = total > 0 ? (double) current / total : 0;
        int filled = (int) (progress * width);
        String bar = "█".repeat(filled) + "░".repeat(width - filled);
        System.out.printf("[%s] %d/%d (%.1f%%)%n", bar, current, total, progress * 100);
    }

    public static void printSeparator() {
        System.out.println("─".repeat(40));
    }
}
