package solver;

import fields.Field;

import java.util.ArrayList;
import java.util.List;

public class Solver<T> {
    private boolean hasOneSolution = true;
    private boolean hasInfinitelyManySolutions = false;

    public boolean isOneSolution() {
        return hasOneSolution;
    }

    public boolean isInfinitelyManySolutions() {
        return hasInfinitelyManySolutions;
    }

    Matrix<T> m;
    Field<T> f;
    int variables;
    int equations;

    public Solver(Matrix<T> m, int variables, int equations) {
        try {
            this.m = m.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        this.f = m.f;
        this.variables = variables;
        this.equations = equations;
    }

    public Solver(T[][] arr, Field<T> f, int variables, int equations) {
        this(new Matrix<T>(arr, f), variables, equations);
    }

    public List<T> getSolution() {
        m.reducedRowEchelonForm();
        for (int row = m.rows - 1; row >= 0; row--) {
            if (isNoSolutionRow(row)) {
                hasOneSolution = false;
                return null;
            }
            if (isInfSolutionsRow(row)) {
                hasOneSolution = false;
                hasInfinitelyManySolutions = true;
                return null;
            }
        }
        List<T> result = new ArrayList<T>();
        for (int var = 0; var < variables; var++)
            result.add(m.elements[var][m.cols - 1]);
        return result;
    }

    private boolean isNoSolutionRow(int row) {
        for (int col = 0; col < m.cols - 1; col++)
            if (!f.equals(m.elements[row][col], f.zero())) {
                return false;
            }
        return !f.equals(m.elements[row][m.cols - 1], f.zero());
    }

    private boolean isInfSolutionsRow(int row) {
        int col = 0;
        while (col < m.cols - 1 && !f.equals(m.elements[row][col], f.one()))
            col++;
        for (int j = col + 1; j < m.cols - 1; j++) {
            if (!f.equals(m.elements[row][j], f.zero()))
                return true;
        }
        return false;
    }
}
