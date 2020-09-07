package numbers;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex {
    // public static final String I = "\uD835\uDC8A";
    public static final String I = "i";
    private static final double ERROR = 1e-8;
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex parseComplex(String s) throws NumberFormatException {
        String doubleRegex = "[+-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
        s = s.trim().replaceAll("\\s", "");
        double re;
        double im;
        if (s.endsWith(I)) {
            s = s.substring(0, s.length() - 1);
            Matcher m = Pattern.compile(doubleRegex).matcher(s);
            if (m.lookingAt()) {
                double n = Double.parseDouble(m.group());
                s = s.substring(m.group().length());
                if (s.length() > 0) {
                    if ("+".equals(s)) {
                        return new Complex(n, 1);
                    }
                    if ("-".equals(s)) {
                        return new Complex(n, -1);
                    }
                    im = Double.parseDouble(s);
                    return new Complex(n, im);
                } else {
                    // imaginary part only
                    return new Complex(0, n);
                }
            } else {
                // i or -i only
                if (s.length() == 0)
                    return new Complex(0, 1);
                else {
                    if ("-".equals(s))
                        return new Complex(0, -1);
                    else
                        throw new NumberFormatException();
                }
            }
        } else {
            // real part only
            re = Double.parseDouble(s);
            return new Complex(re, 0);
        }
    }

    // (a+bi) + (c+di) = (a+c) + (b+d)i
    public Complex add(Complex that) {
        return new Complex(re + that.re, im + that.im);
    }

    // (a+bi) -> (-a-bi)
    public Complex negate() {
        return new Complex(-re, -im);
    }

    // (a+bi) * (c+di) = (ac−bd) + (ad+bc)i
    public Complex multiply(Complex that) {
        return new Complex(re * that.re - im * that.im,
                re * that.im + im * that.re);
    }

    // (a+bi) -> (a-bi)
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    // (a+bi) -> (a-bi) / (a*a+b*b)
    public Complex reciprocal() {
        return new Complex(re / (re * re + im * im), -im / (re * re + im * im));
    }

    // (a+bi) / (c+di) = (a*c+b*d)/(c*c+d*d) + (c*b-a*d)/(c*c+d*d)i
    public Complex divide(Complex that) {
        double divider = that.re * that.re + that.im * that.im;
        return new Complex((re * that.re + im * that.im) / divider,
                (that.re * im - re * that.im) / divider);
    }

    // (a+bi) -> sqrt(a*a + b*b)
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Complex))
            return false;
        Complex that = (Complex) obj;
        return (Math.abs(re - that.re) < ERROR) && (Math.abs(im - that.im) < ERROR);
    }

    @Override
    public String toString() {
        // TODO переписать эту срань
        String r = "";
        String i = "";
        String sign = "";
        if (re == 0.0 && im == 0.0)
            return "0";

        if (im != 0.0) {
            if (im != 1.0 && im != -1.0)
                i = ((long) im == im ? "" + Math.abs((long) im) : "" + Math.abs(im)) + I;
            else
                i = I;
            sign = (im >= 0) ? "+" : "-";
        }
        if (re != 0.0)
            r = (long) re == re ? "" + (long) re : "" + re;
        else if (sign.equals("+"))
            sign = "";

        return "" + r + sign + i;
    }
}
