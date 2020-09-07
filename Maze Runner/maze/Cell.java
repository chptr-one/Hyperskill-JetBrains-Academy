package maze;

import java.io.*;
import java.util.*;

public class Cell implements Serializable {
    private final int row;
    private final int col;

    private Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Cell getInstance(int row, int col) {
        return new Cell(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "{" + row + "," + col + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
