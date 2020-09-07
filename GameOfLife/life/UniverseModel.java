package life;

import java.util.*;

class UniverseModel {
    private final int size;
    private boolean[][] cells; // true = alive, false = dead;
    private int alive;
    private int generation;

    UniverseModel(int size) {
        this.size = size;
        cells = new boolean[size][size];
        populateRandomly();
    }

    void populateRandomly() {
        alive = 0;
        generation = 1;
        Random random = new Random();
        for (boolean[] row : cells) {
            for (int i = 0; i < row.length; i++) {
                row[i] = random.nextBoolean();
                if (row[i]) alive++;
            }
        }
    }

    boolean[][] getCells() {
        return cells.clone();
    }

    int getAlive() {
        return alive;
    }

    int getGeneration() {
        return generation;
    }

    void evolve() {
        boolean[][] nextState = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int aliveNeighbours = countAliveNeighbours(row, col);

                if (cells[row][col]) { // cell is alive
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        nextState[row][col] = true;
                    } else {
                        nextState[row][col] = false;
                        alive--;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        nextState[row][col] = true; // Arise, go thy way (Luke 17:19)
                        alive++;
                    } else {
                        nextState[row][col] = false;
                    }
                }
            }
        }
        generation++;
        cells = nextState;
    }

    private int countAliveNeighbours(int row, int col) {
        int result = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (cells[i < 0 ? size - 1 : i == size ? 0 : i]
                        [j < 0 ? size - 1 : j == size ? 0 : j]) {
                    result++;
                }
            }
        }
        // We also counted the argument cell, so, if this cell is alive, we subtract its value from the result
        return result - (cells[row][col] ? 1 : 0);
    }
}
