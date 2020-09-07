package maze;

import java.io.*;

public class MazeRunner {
    private Maze maze;

    public boolean isMazeExists() {
        return maze != null;
    }

    public void generate(int size) {
        maze = new Maze(size, size);
        maze.generate();
    }

    public void load(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            maze = (Maze) ois.readObject();
        }
    }

    public void save(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(maze);
        }
    }

    public void display() {
        maze.print();
    }

    public void findEscape() {
        maze.printEscape();
    }
}
