package fields;

import numbers.Complex;

public class ComplexField implements Field<Complex> {
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    private static final ComplexField F = new ComplexField();

    public static Field<Complex> getField() {
        return F;
    }

    @Override
    public Complex zero() {
        return ZERO;
    }

    @Override
    public Complex one() {
        return ONE;
    }

    @Override
    public Complex negate(Complex x) {
        return x.negate();
    }

    @Override
    public Complex add(Complex x, Complex y) {
        return x.add(y);
    }

    @Override
    public Complex reciprocal(Complex x) {
        return x.reciprocal();
    }

    @Override
    public Complex multiply(Complex x, Complex y) {
        return x.multiply(y);
    }

    @Override
    public boolean equals(Complex x, Complex y) {
        return x.equals(y);
    }
}

