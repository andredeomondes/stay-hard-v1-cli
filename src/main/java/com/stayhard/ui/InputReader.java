package com.stayhard.ui;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    public int readInt() {
        while (true) {
            try {
                String input = readLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        return readInt();
    }

    public int readIntInRange(String prompt, int min, int max) {
        int value;
        while (true) {
            value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("Please enter a number between %d and %d%n", min, max);
        }
    }

    public boolean readBoolean(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = readLine().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    public void close() {
        scanner.close();
    }
}
