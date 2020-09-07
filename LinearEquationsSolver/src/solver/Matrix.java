package solver;

import fields.Field;

import java.util.Arrays;

public class Matrix<E> implements Cloneable {
    public final int rows;
    public final int cols;
    public final Field<E> f;
    public E[][] elements;

    private Matrix(int rows, int cols, Field<E> f) {
        this.rows = rows;
        this.cols = cols;
        this.f = f;
        elements = (E[][]) new Object[rows][cols];
    }

    // Elements of an array must be non-null
    public Matrix(E[][] array, Field<E> f) {
        this(array.length, array[0].length, f);
        for (int row = 0; row < rows; row++) {
            System.arraycopy(array[row], 0, elements[row], 0, cols);
        }
    }

    /*
    Matrix row operations
    */

    // Swaps the two rows of this matrix
    public void swapRows(int first, int second) {
        if (first < 0 || first >= rows || second < 0 || second >= rows)
            throw new IndexOutOfBoundsException("Row index out of bounds");
        E[] temp = elements[first];
        elements[first] = elements[second];
        elements[second] = temp;
    }

    // Multiplies the row in this matrix by the factor
    public void multiplyRow(int row, E factor) {
        if (row < 0 || row >= rows)
            throw new IndexOutOfBoundsException("Row index out of bounds");
        for (int col = 0; col < cols; col++)
            elements[row][col] = f.multiply(elements[row][col], factor);
    }

    // Adds the source row in this matrix multiplied by the factor to the destination row
    public void addRows(int srcRow, int destRow, E factor) {
        if (srcRow < 0 || srcRow >= rows || destRow < 0 || destRow >= rows)
            throw new IndexOutOfBoundsException("Row index out of bounds");
        for (int col = 0; col < cols; col++)
            elements[destRow][col] = f.add(elements[destRow][col], f.multiply(elements[srcRow][col], factor));
    }

    /*
    Advanced matrix operations
    */

    // Converts this matrix to reduced row echelon form (RREF) using Gauss-Jordan elimination.
    public void reducedRowEchelonForm() {
        // Compute row echelon form (REF)
        int numPivots = 0;
        for (int j = 0; j < cols && numPivots < rows; j++) {  // For each column
            // Find a pivot row for this column
            int pivotRow = numPivots;
            while (pivotRow < rows && elements[pivotRow][j].equals(f.zero()))
                pivotRow++;
            if (pivotRow == rows)
                continue;  // Cannot eliminate on this column
            swapRows(numPivots, pivotRow);
            pivotRow = numPivots;
            numPivots++;

            // Simplify the pivot row
            multiplyRow(pivotRow, f.reciprocal(elements[pivotRow][j]));

            // Eliminate rows below
            for (int i = pivotRow + 1; i < rows; i++)
                addRows(pivotRow, i, f.negate(elements[i][j]));
        }

        // Compute reduced row echelon form (RREF)
        for (int i = numPivots - 1; i >= 0; i--) {
            // Find pivot
            int pivotCol = 0;
            while (pivotCol < cols && f.equals(elements[i][pivotCol], f.zero()))
                pivotCol++;
            if (pivotCol == cols)
                continue;  // Skip this all-zero row

            // Eliminate rows above
            for (int j = i - 1; j >= 0; j--)
                addRows(i, j, f.negate(elements[j][pivotCol]));
        }
    }

    @Override
    protected Matrix<E> clone() throws CloneNotSupportedException {
        super.clone();
        return new Matrix<>(elements, f);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (E[] row : elements) {
            result.append(Arrays.toString(row));
            result.append('\n');
        }
        return result.toString();
    }
}
