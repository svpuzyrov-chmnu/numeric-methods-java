package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

public record Result(double x) {

    public double roundBy(double tolerance) {
        var digits = new DefaultSignDigitsTolerance().getByTolerance(tolerance);

        return new DefaultSignDigitsRoundable(x).roundTo(digits);
    }

    @Override
    public String toString() {
        return Double.toString(x);
    }
}
