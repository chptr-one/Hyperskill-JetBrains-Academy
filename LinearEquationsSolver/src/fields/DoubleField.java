package fields;

public class DoubleField implements Field<Double> {
    public static final Double ZERO = 0.0;
    public static final Double ONE = 1.0;
    private static final DoubleField F = new DoubleField();

    // used in equals() method
    private static final double ERROR = 1e-10;

    public static Field<Double> getField() {
        return F;
    }

    @Override
    public Double zero() {
        return ZERO;
    }

    @Override
    public Double one() {
        return ONE;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double reciprocal(Double x) {
        return 1 / x;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public boolean equals(Double x, Double y) {
        return Math.abs(x - y) < ERROR;
    }
}
