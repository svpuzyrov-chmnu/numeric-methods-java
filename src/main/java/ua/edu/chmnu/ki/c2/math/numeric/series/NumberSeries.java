package ua.edu.chmnu.ki.c2.math.numeric.series;

public interface NumberSeries extends IterableSeries {

    default double compute(double tolerance) {
        throw new UnsupportedOperationException();
    }

}
