package domain.utils;

import java.util.Scanner;

public class ConsoleVisual {

    // Cores e Estilos ANSI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";      // Identidade Stay Hard
    public static final String GREEN = "\u001B[32m";    // Sucesso
    public static final String YELLOW = "\u001B[33m";   // Alerta
    public static final String BLUE = "\u001B[34m";     // Infos
    public static final String CYAN = "\u001B[36m";     // Input/Destaque
    public static final String BOLD = "\u001B[1m";

    private static final String LINHA = "--------------------------------------------";

    /**
     * Cabeçalho principal com título dinâmico
     */
    public static void printHeader(String title) {
        clearCanvas();
        System.out.println(RED + BOLD + LINHA + RESET);
        int padding = (44 - title.length()) / 2;
        System.out.printf(RED + BOLD + "%" + (padding + title.length()) + "s%n" + RESET, title.toUpperCase());
        System.out.println(RED + BOLD + LINHA + RESET);
    }

    /**
     * Limpa o console para uma navegação fluida
     */
    public static void clearCanvas() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // --- MENSAGENS DE STATUS ---

    public static void success(String msg) {
        System.out.println(GREEN + "[✔] SUCCESS: " + RESET + msg);
    }

    public static void error(String msg) {
        System.out.println(RED + BOLD + "[✘] ERROR: " + RESET + msg);
    }

    public static void alert(String msg) {
        System.out.println(YELLOW + "[!] ALERT: " + RESET + msg);
    }

    public static void info(String msg) {
        System.out.println(BLUE + "[i] INFO: " + RESET + msg);
    }

    /**
     * Padroniza a leitura de dados com cor diferenciada no prompt
     */
    public static String input(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.print(CYAN + BOLD + "> " + prompt + ": " + RESET);
        return sc.nextLine();
    }

    /**
     * Cria um divisor visual simples
     */
    public static void divider() {
        System.out.println(RED + "............................................" + RESET);
    }
}