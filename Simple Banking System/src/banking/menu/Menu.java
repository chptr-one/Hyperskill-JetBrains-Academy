package banking.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final List<MenuItem> items;
    private Scanner scanner;
    private boolean exit;

    public Menu(Scanner scanner) {
        this.items = new ArrayList<>();
        this.scanner = scanner;
        this.exit = false;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public MenuItem selectItem() {
        printMenu();
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(items.get(items.size() - 1));
    }

    public void setExit() {
        exit = true;
    }

    public boolean notExit() {
        return !exit;
    }

    private void printMenu() {
        items.forEach(System.out::println);
    }
}
