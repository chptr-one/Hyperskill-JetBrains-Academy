package fields;

/*
 Field represents an abstract algebra, where all that axioms exists:
 0 is an element of field and 0 + x = x;
 1 is an element of field and 1 * x = x;
 x + y = y + x
 x * y = y * x
 (x + y) + z = x + (y + z)
 (x * y) * z = x * (y * z)
 x * (y + z) = (x * y) + (x * z)
 -x = 0 - x (in other words x + (-x) = o)
 If x != 0, then x / 1 is an element of the field, such that x * (x / 1) = 1

 Field objects and field element objects must be immutable.
 All methods must return a non-null value and must throw NullPointerException if any argument is null
 */

public interface Field<T> {

    T zero();

    T one();

    // result = -x
    T negate(T x);

    // result = x + y
    T add(T x, T y);

    // result = x - y
    default T subtract(T x, T y) {
        return add(x, negate(y));
    }

    // result = 1 / x
    T reciprocal(T x);

    // result = x * y
    T multiply(T x, T y);

    // result = x / y
    default T divide(T x, T y) {
        return multiply(x, reciprocal(y));
    }

    /*
     Elements x and y are not required to implement their own equals() correctly.
	 This means x.equals(y) is allowed to mismatch field.equals(x, y)
     */

    boolean equals(T x, T y);
}
