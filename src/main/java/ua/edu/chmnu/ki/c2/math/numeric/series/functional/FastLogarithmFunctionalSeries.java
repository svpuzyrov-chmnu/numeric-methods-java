package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.Result;

public class FastLogarithmFunctionalSeries extends DefaultFunctionalSeries implements FunctionalSeries {

    public FastLogarithmFunctionalSeries() {
        this(0);
    }

    public FastLogarithmFunctionalSeries(int startNumber) {
        super(startNumber, x -> x, (x, n) -> x * x * (2.0 * n + 1.0) / (2.0 * n + 3.0));
    }

    @Override
    public Result compute(double x, double tolerance) {
        return new Result(2 * super.compute(x, tolerance).x());
    }

    @Override
    protected double transformArgumentTo(double x) {
        return (x - 1) / (x + 1);
    }
}
