package banking.menu;

import java.util.Scanner;

public class MenuBuilder {

    private final Menu menu;

    public MenuBuilder() {
        menu = new Menu(new Scanner(System.in));
    }

    public MenuBuilder setScanner(Scanner scanner) {
        menu.setScanner(scanner);
        return this;
    }

    public MenuBuilder addItem(MenuItem item) {
        menu.addItem(item);
        return this;
    }

    public MenuBuilder addItem(int id, String name, MenuAction action) {
        menu.addItem(new MenuItem(id, name, action));
        return this;
    }

    public Menu build() {
        return menu;
    }
}
