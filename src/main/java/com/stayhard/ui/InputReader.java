package com.stayhard.ui;

import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.utils.ConsoleVisual;

import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt(String message) {
        while (true) {
            try {
                System.out.print(ConsoleVisual.CYAN + ConsoleVisual.BOLD + "> " + message + ": " + ConsoleVisual.RESET);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                ConsoleVisual.error("Digite um número válido.");
            }
        }
    }

    public String readString(String message) {
        System.out.print(ConsoleVisual.CYAN + ConsoleVisual.BOLD + "> " + message + ": " + ConsoleVisual.RESET);
        return scanner.nextLine().trim();
    }

    public Priority readPriority() {
        System.out.println("Prioridade:");
        System.out.println("1 - LOW");
        System.out.println("2 - MEDIUM");
        System.out.println("3 - HIGH");
        ConsoleVisual.divider();

        int option = readInt("Escolha a prioridade");

        return switch (option) {
            case 1 -> Priority.LOW;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.HIGH;
            default -> null;
        };
    }

    public void pressEnterToContinue() {
        System.out.print(ConsoleVisual.YELLOW + "Pressione ENTER para continuar..." + ConsoleVisual.RESET);
        scanner.nextLine();
    }
}
