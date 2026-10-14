package ua.edu.chmnu.ki.c2.math.numeric.equation;

import java.util.function.DoubleUnaryOperator;

public record Range(double left, double right) {

    public double center() {
        return left + (right - left)/2;
    }

    public Range centerLeft() {
        return new Range(left, center());
    }

    public Range centerRight() {
        return new Range(center(), right);
    }

    public Range map(DoubleUnaryOperator f) {
        return new Range(
          f.applyAsDouble(left),
          f.applyAsDouble(right)
        );
    }

    public boolean lengthIsLessOrEq(double tolerance) {
        return Math.abs(right - left) <= tolerance;
    }

    public double length() {
        return right - left;
    }

    public boolean isBorder(double x) {
        return x == left || x == right;
    }
}
