package maze;

import java.io.*;
import java.util.*;

public class Menu {
    private final MazeRunner mazeRunner;
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private boolean exit = false;

    public Menu(MazeRunner mazeRunner) {
        this.mazeRunner = mazeRunner;
    }

    public void action() {
        display();
        int command = readCommand();
        if (command == 0) {
            exit = true;
            return;
        }
        switch (command) {
            case 1: {
                int size = readSize();
                if (size != -1) {
                    mazeRunner.generate(size);
                    mazeRunner.display();
                } else {
                    System.out.println("Size can not be less then 3");
                }
                break;
            }
            case 2: {
                String fileName = readFileName();
                if (!"".equals(fileName)) {
                    try {
                        mazeRunner.load(fileName);
                    } catch (IOException e) {
                        System.out.println("The file " + fileName + " does not exist");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Cannot load the maze. It has an invalid format");
                    }
                }
                break;
            }
            case 3: {
                String fileName = readFileName();
                try {
                    mazeRunner.save(fileName);
                } catch (IOException e) {
                    System.out.println("Cannot save the maze to file " + fileName);
                }
                break;
            }
            case 4: {
                mazeRunner.display();
                break;
            }
            case 5: {
                mazeRunner.findEscape();
                break;
            }
            default: {
                System.out.println("Incorrect option. Please try again");
            }
        }
    }

    public boolean notExit() {
        return !exit;
    }

    private void display() {
        generateMenuItems();
        System.out.println("=== Menu ===");
        for (MenuItem item : menuItems)
            System.out.println(item.number + ". " + item.text);
    }

    private void generateMenuItems() {
        menuItems.clear();
        menuItems.add(new MenuItem(1, "Generate a new maze"));
        menuItems.add(new MenuItem(2, "Load a maze"));
        if (mazeRunner.isMazeExists()) {
            menuItems.add(new MenuItem(3, "Save the maze"));
            menuItems.add(new MenuItem(4, "Display the maze"));
            menuItems.add(new MenuItem(5, "Find the escape"));
        }
        menuItems.add(new MenuItem(0, "Exit"));
    }

    private int readCommand() {
        if (scanner.hasNextInt()) {
            int command = scanner.nextInt();
            if (menuItems.stream().map(i -> i.number).anyMatch(i -> i == command)) {
                return command;
            }
        } else {
            scanner.next();
        }
        return -1;
    }

    private int readSize() {
        System.out.println("Enter the size of a new maze");
        if (scanner.hasNextInt()) {
            int size = scanner.nextInt();
            if (size >= 3) {
                return size;
            }
        } else {
            scanner.next();
        }
        return -1;
    }

    private String readFileName() {
        System.out.println("Enter file name");
        return scanner.next();
    }
}

class MenuItem {
    final int number;
    final String text;

    MenuItem(int number, String text) {
        this.number = number;
        this.text = text;
    }
}