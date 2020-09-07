package solver;

import fields.ComplexField;
import fields.DoubleField;
import fields.RationalField;
import numbers.Complex;
import numbers.Rational;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static <T> void printResult(Solver<T> solver) {
        List<T> result = solver.getSolution();
        if (solver.isOneSolution()) {
            System.out.println("Solution:");
            result.forEach(System.out::println);
        } else {
            if (solver.isInfinitelyManySolutions()) {
                System.out.println("Infinitely many solutions");
            } else
                System.out.println("No solutions");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int variables;
        int equations;
        Double[][] doubles;
        try (Scanner in = new Scanner(new File("/home/peter/in.txt"))) {
            variables = in.nextInt();
            equations = in.nextInt();
            doubles = new Double[equations][variables + 1];

            for (int row = 0; row < equations; row++) {
                for (int col = 0; col <= variables; col++)
                    doubles[row][col] = in.nextDouble();
            }

        }
        Matrix<Double> doubleMatrix = new Matrix<>(doubles, DoubleField.getField());
        System.out.println("Matrix of doubles");
        System.out.println(doubleMatrix);
        Solver<Double> solver = new Solver<>(doubleMatrix, variables, equations);
        printResult(solver);

        Rational[][] rationals = new Rational[equations][variables+1];
        for (int row = 0; row < equations; row++) {
            for (int col = 0; col <= variables; col++)
                rationals[row][col] = new Rational(doubles[row][col]);
        }
        Matrix<Rational> rationalMatrix = new Matrix<>(rationals, RationalField.getField());
        System.out.println();
        System.out.println("Matrix of rationals");
        System.out.println(rationalMatrix);
        Solver<Rational> rationalSolver = new Solver<>(rationalMatrix, variables, equations);
        printResult(rationalSolver);

        Complex[][] complexes;
        try (Scanner in = new Scanner(new File("/home/peter/complex.txt"))) {
            variables = in.nextInt();
            equations = in.nextInt();
            complexes = new Complex[equations][variables + 1];

            for (int row = 0; row < equations; row++) {
                for (int col = 0; col <= variables; col++)
                    complexes[row][col] = Complex.parseComplex(in.next());
            }
        }
        Matrix<Complex> complexMatrix = new Matrix<>(complexes, ComplexField.getField());
        System.out.println();
        System.out.println("Matrix of complex numbers");
        System.out.println(complexMatrix);
        Solver<Complex> complexSolver = new Solver<>(complexMatrix, variables, equations);
        printResult(complexSolver);
    }
}
