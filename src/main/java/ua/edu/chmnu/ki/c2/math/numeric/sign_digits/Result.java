package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

import java.util.Objects;

public final class Result {
    private final double x;

    public Result(double x) {
        this.x = x;
    }

    public double x() {
        return x;
    }

    public double roundBy(double tolerance) {
        var digits = new DefaultSignDigitsTolerance().getByTolerance(tolerance);

        return new DefaultSignDigitsRoundable(x).roundTo(digits);
    }

    @Override
    public String toString() {
        return Double.toString(x);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Result result)) {
            return false;
        }
        return Double.compare(x, result.x) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
