package budget.menu;

import budget.controller.OperationController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {

    public final static MenuItem EXIT = new MenuItem("Exit", null);

    private final Scanner scanner;
    private final Map<Integer, MenuItem> items;
    private boolean exit;

    public MainMenu(Scanner scanner, OperationController controller) {
        this.scanner = scanner;
        exit = false;

        items = new LinkedHashMap<>();
        items.put(1, new MenuItem("Add income", controller::addIncome));
        items.put(2, new MenuItem("Add purchase", controller::addPurchase));
        items.put(3, new MenuItem("Show list of purchases", controller::showListOfPurchases));
        items.put(4, new MenuItem("Balance", controller::printBalance));
        items.put(5, new MenuItem("Save", controller::save));
        items.put(6, new MenuItem("Load", controller::load));
        items.put(7, new MenuItem("Analyze (Sort)", controller::analyze));

        items.put(0, EXIT);
    }

    public boolean notExit() {
        return !exit;
    }

    public void makeChoice() {
        printMenu();
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println();

        var selected = items.getOrDefault(id, EXIT);
        if (selected == EXIT) {
            System.out.println("Bye!");
            exit = true;
        } else {
            items.get(id).getAction().execute();
        }
    }

    private void printMenu() {
        System.out.println("Choose your action:");
        for (var item : items.entrySet()) {
            System.out.println(item.getKey() + ") " + item.getValue().getName());
        }
    }

    private static class MenuItem {

        final String name;
        final MenuAction action;

        MenuItem(String name, MenuAction action) {
            this.name = name;
            this.action = action;
        }

        String getName() {
            return name;
        }

        MenuAction getAction() {
            return action;
        }
    }

}
