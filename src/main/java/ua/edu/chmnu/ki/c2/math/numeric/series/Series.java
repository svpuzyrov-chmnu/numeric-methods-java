package ua.edu.chmnu.ki.c2.math.numeric.series;

public interface Series {

    default double compute(double tolerance) {
        throw new UnsupportedOperationException();
    }

    default double compute(double x, double tolerance) {
        throw new UnsupportedOperationException();
    }

    int countOfIterations();
}
