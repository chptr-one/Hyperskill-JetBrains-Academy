package banking.menu;

public class MenuItem {

    private final int id;
    private final String name;
    private final MenuAction action;

    public MenuItem(int id, String name, MenuAction action) {
        this.id = id;
        this.name = name;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MenuAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
