package fields;

import numbers.Rational;

public class RationalField implements Field<Rational> {
    public static final Rational ZERO = new Rational(0,1);
    public static final Rational ONE = new Rational(1,1);
    private static final RationalField F = new RationalField();

    public static Field<Rational> getField() {return F;}

    @Override
    public Rational zero() {
        return ZERO;
    }

    @Override
    public Rational one() {
        return ONE;
    }

    @Override
    public Rational negate(Rational x) {
        return x.negate();
    }

    @Override
    public Rational add(Rational x, Rational y) {
        return x.add(y);
    }

    @Override
    public Rational reciprocal(Rational x) {
        return x.reciprocal();
    }

    @Override
    public Rational multiply(Rational x, Rational y) {
        return x.multiply(y);
    }

    @Override
    public boolean equals(Rational x, Rational y) {
        return x.equals(y);
    }
}
