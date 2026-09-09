package ua.edu.chmnu.ki.c2.math.numeric.series;

public interface FunctionalSeries extends IterableSeries {

    default double compute(double x, double tolerance) {
        throw new UnsupportedOperationException();
    }
}
