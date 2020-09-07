package numbers;

import java.util.Objects;

public class Rational {
    private long num;
    private long den;

    public Rational(long num, long den) {
        if (den == 0) throw new NumberFormatException("Denominator can not be 0");
        if (den < 0) throw new NumberFormatException("Denominator can not be less then 0");
        this.num = num;
        this.den = den;
        reduce();
    }

    public Rational(double x) {
        if (x == 0) {
            num = 0;
            den = 1;
            return;
        }

        long bits = Double.doubleToLongBits(x);
        long sign = bits >>> 63;
        long exponent = ((bits >>> 52) ^ (sign << 11)) - 1023;
        long fraction = bits << 12;

        long a = 1L;
        long b = 1L;

        for (int i = 63; i >= 12; i--) {
            a = a * 2 + ((fraction >>> i) & 1);
            b *= 2;
        }

        if (exponent > 0)
            a *= 1 << exponent;
        else
            b *= 1 << -exponent;

        if (sign == 1)
            a *= -1;

        num = a;
        den = b;
        reduce();
    }

    /*public static Rational parseRational(String s) {

    }*/


    // Lowest common denominator
    public static long lcd(Rational a, Rational b) {
        return lcd(a.den, b.den);
    }

    public static long lcd(long den1, long den2) {
        long factor = den1;
        while ((den1 % den2) != 0)
            den1 += factor;
        return den1;
    }

    // Greatest common divisor
    public static long gcd(Rational a, Rational b) {
        return gcd(a.den, b.den);
    }

    public static long gcd(long den1, long den2) {
        long factor;
        while (den2 != 0) {
            factor = den2;
            den2 = den1 % den2;
            den1 = factor;
        }
        return den1;
    }

    // reduce fraction to an equivalent one based on a greatest common denominator
    private void reduce() {
        long common;
        long n = Math.abs(num);
        long d = Math.abs(den);
        if (n > d)
            common = gcd(n, d);
        else if (n < d)
            common = gcd(d, n);
        else
            common = n;
        num /= common;
        den /= common;
    }

    public Rational negate() {
        return new Rational(-num, den);
    }

    public Rational reciprocal() {
        return new Rational(den, num);
    }

    public Rational add(Rational that) {
        return new Rational(num * that.den + that.num * den, den * that.den);
    }

    public Rational subtract(Rational that) {
        return add(that.negate());
    }

    public Rational multiply(Rational that) {
        return new Rational(num * that.num, den * that.den);
    }

    public Rational divide(Rational that) {
        return multiply(that.reciprocal());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rational))
            return false;
        Rational that = (Rational) obj;
        return num == that.num && den == that.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    public static void main(String[] args) {
        Rational f1 = new Rational(12, 54);
        Rational f2 = new Rational(8, 24);
        System.out.println(lcd(f1, f2));
        System.out.println(gcd(f1, f2));
        double[] doubles = {0, -0, 0.5, -3e1};
        for (double d : doubles)
            System.out.println(d + " -> " + new Rational(d));
    }
}
