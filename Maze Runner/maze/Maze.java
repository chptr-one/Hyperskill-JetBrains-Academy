package maze;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Maze implements Serializable {
    private final static String PASS = "  ";
    private final static String WALL = "\u2588\u2588";
    private final int rowsToPrint;
    private final int colsToPrint;
    private final Graph<Cell> graph;
    private final int rows;
    private final int cols;
    private Cell entrance;
    private Cell exit;

    public Maze(int rows, int cols) {
        if (rows < 3 || cols < 3) throw new IllegalArgumentException();
        rowsToPrint = rows;
        colsToPrint = cols;
        this.rows = (rows - 1) / 2;
        this.cols = (cols - 1) / 2;
        graph = new Graph<>();
        entrance = null;
        exit = null;
    }

    // Recursive back tracker
    public void generate() {
        final Random random = new Random();
        final Deque<Cell> stack = new LinkedList<>();

        entrance = Cell.getInstance(0, 0);
        exit = Cell.getInstance(rows - 1, cols - 1);

        Cell current = entrance;
        stack.push(current);
        graph.addVertex(current);

        while (!stack.isEmpty()) {
            List<Cell> neighbours = getUnvisitedNeighbors(current);
            if (!neighbours.isEmpty()) {
                Cell randomNeighbor = neighbours.get(random.nextInt(neighbours.size()));
                graph.addVertex(randomNeighbor);
                graph.addEdge(current, randomNeighbor);
                current = randomNeighbor;
                stack.push(current);
            } else {
                current = stack.pop();
            }
        }
    }

    public List<Cell> findEscape() {
        final Set<Cell> visited = new HashSet<>();
        final Deque<Cell> path = new LinkedList<>();

        Cell current = entrance;
        visited.add(current);
        while (!current.equals(exit)) {
            List<Cell> unvisitedNeighbours = graph.adjacency(current)
                    .stream()
                    .filter(cell -> !visited.contains(cell))
                    .collect(Collectors.toList());
            if (unvisitedNeighbours.size() > 0) {
                path.push(current);
                current = unvisitedNeighbours.get(0);
                visited.add(current);
            } else {
                current = path.pop();
            }
        }
        path.push(current);
        return new ArrayList<>(path);
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        List<Cell> result = new ArrayList<>();

        Cell neighbor;
        if (row > 0) {
            neighbor = Cell.getInstance(row - 1, col);
            if (!graph.containsVertex(neighbor))
                result.add(neighbor);
        }
        if (row < rows - 1) {
            neighbor = Cell.getInstance(row + 1, col);
            if (!graph.containsVertex(neighbor))
                result.add(neighbor);
        }
        if (col > 0) {
            neighbor = Cell.getInstance(row, col - 1);
            if (!graph.containsVertex(neighbor))
                result.add(neighbor);
        }
        if (col < cols - 1) {
            neighbor = Cell.getInstance(row, col + 1);
            if (!graph.containsVertex(neighbor))
                result.add(neighbor);
        }

        return result;
    }

    public void print() {
        int[][] matrix = new int[rowsToPrint][colsToPrint];
        for (int[] row : matrix) {
            Arrays.fill(row, 1);
        }

        for (Cell cell : graph.getVertices()) {
            int cellRow = cell.getRow();
            int cellCol = cell.getCol();
            matrix[cellRow * 2 + 1][cellCol * 2 + 1] = 0;
            for (Cell neighbour : graph.adjacency(cell)) {
                int deltaRow = neighbour.getRow() - cellRow;
                int deltaCol = neighbour.getCol() - cellCol;
                matrix[(cellRow * 2 + 1) + deltaRow][(cellCol * 2 + 1) + deltaCol] = 0;
            }
        }

        matrix[entrance.getRow() * 2][entrance.getCol() * 2 + 1] = 0;
        matrix[exit.getRow() * 2 + 2][exit.getCol() * 2 + 1] = 0;
        if (rowsToPrint % 2 == 0) {
            matrix[0][entrance.getCol() * 2 + 1] = 0;
            matrix[exit.getRow() * 2 + 3][exit.getCol() * 2 + 1] = 0;
        }

        for (int[] row : matrix) {
            for (int i : row) {
                System.out.print(i == 1 ? WALL : PASS);
            }
            System.out.println();
        }
    }

    public void printEscape() {
        int[][] matrix = new int[rowsToPrint][colsToPrint];
        for (int[] row : matrix) {
            Arrays.fill(row, 1);
        }

        for (Cell cell : graph.getVertices()) {
            int cellRow = cell.getRow();
            int cellCol = cell.getCol();
            matrix[cellRow * 2 + 1][cellCol * 2 + 1] = 0;
            for (Cell neighbour : graph.adjacency(cell)) {
                int deltaRow = neighbour.getRow() - cellRow;
                int deltaCol = neighbour.getCol() - cellCol;
                matrix[(cellRow * 2 + 1) + deltaRow][(cellCol * 2 + 1) + deltaCol] = 0;
            }
        }

        List<Cell> path = findEscape();
        for (int i = 0; i < path.size() - 1; i++) {
            int row = path.get(i).getRow();
            int col = path.get(i).getCol();
            matrix[row * 2 + 1][col * 2 + 1] = -1;
            int deltaRow = path.get(i + 1).getRow() - row;
            int deltaCol = path.get(i + 1).getCol() - col;
            matrix[row * 2 + 1 + deltaRow][col * 2 + 1 + deltaCol] = -1;
        }

        matrix[entrance.getRow() * 2 + 1][entrance.getCol() * 2 + 1] = -1;
        matrix[entrance.getRow() * 2][entrance.getCol() * 2 + 1] = -1;
        matrix[exit.getRow() * 2 + 2][exit.getCol() * 2 + 1] = -1;
        if (rowsToPrint % 2 == 0) {
            matrix[0][entrance.getCol() * 2 + 1] = -1;
            matrix[exit.getRow() * 2 + 3][exit.getCol() * 2 + 1] = -1;
        }

        for (int[] row : matrix) {
            for (int i : row) {
                System.out.print(i == 1 ? WALL : i == 0 ? PASS : "//");
            }
            System.out.println();
        }
    }
}
