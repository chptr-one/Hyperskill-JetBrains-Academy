package budget;

import budget.controller.OperationController;
import budget.menu.MainMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        var controller = new OperationController(scanner);
        var mainMenu = new MainMenu(scanner, controller);

        while (mainMenu.notExit()) {
            mainMenu.makeChoice();
        }
    }
}
