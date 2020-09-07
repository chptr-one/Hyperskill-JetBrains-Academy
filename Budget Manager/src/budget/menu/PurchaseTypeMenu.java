package budget.menu;

import budget.domain.PurchaseType;
import budget.repository.PurchaseTypeRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PurchaseTypeMenu {

    public final static PurchaseType ALL = new PurchaseType("All");
    public final static PurchaseType EXIT = new PurchaseType("Exit");

    private final Scanner scanner;
    private final Map<Integer, PurchaseType> items;
    private boolean exit;

    public PurchaseTypeMenu(Scanner scanner, PurchaseTypeRepository repo, boolean addItemAll, boolean addItemExit) {
        this.scanner = scanner;
        exit = false;

        items = new LinkedHashMap<>();
        var types = repo.getAll();
        int i = 1;
        for (var type : types) {
            items.put(i++, type);
        }

        if (addItemAll) {
            items.put(i++, ALL);
        }

        if (addItemExit) {
            items.put(i, EXIT);
        }
    }

    public boolean notExit() {
        return !exit;
    }

    public PurchaseType makeChoice() {
        printMenu();

        int id = Integer.parseInt(scanner.nextLine());
        System.out.println();

        var selected = items.getOrDefault(id, EXIT);
        if (selected == EXIT) {
            exit = true;
            return null;
        } else {
            return selected;
        }
    }

    private void printMenu() {
        System.out.println("Choose the type of purchase");
        for (var item : items.entrySet()) {
            System.out.println(item.getKey() + ") " + item.getValue().getName());
        }
    }
}
