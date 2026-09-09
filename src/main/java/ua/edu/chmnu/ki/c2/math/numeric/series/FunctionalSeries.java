package ua.edu.chmnu.ki.c2.math.numeric.series;

import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.Result;

public interface FunctionalSeries extends IterableSeries {

    default Result compute(double x, double tolerance) {
        throw new UnsupportedOperationException();
    }

    default double transformToleranceBy(double x, double tolerance) {
        if (x > 1.0) {
            var toleranceOrderShift = (Math.ceil(Math.abs(Math.log10(x))));

            if (toleranceOrderShift >= 1.0) {
                return tolerance/Math.pow(10.0, (int) toleranceOrderShift);
            }
        }

        return tolerance;
    }
}
