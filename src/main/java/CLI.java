import simulation.engine.Engine;

import java.util.Scanner;

public class CLI {
    private final Scanner SCANNER;

    public CLI() {
        this.SCANNER = new Scanner(System.in);
    }

    public void startSimulation() {
        new Engine(
                askForInt("Enter the width of the map: "),
                askForInt("Enter the height of the map: ")
        ).simulate();
    }


    private int askForInt(String message) {
        int value = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(message);
                value = Integer.parseInt(SCANNER.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return value;
    }
}
