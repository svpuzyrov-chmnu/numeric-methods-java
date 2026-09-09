package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.Result;

public class ExponentFunctionalSeries extends DefaultFunctionalSeries implements FunctionalSeries {

    public ExponentFunctionalSeries() {
        this(0);
    }

    public ExponentFunctionalSeries(int startNumber) {
        super(startNumber, _ -> 1.0, (x, n) ->x / (n + 1.0));
    }

    @Override
    public Result compute(double x, double tolerance) {
        if (x > 0) {
            return super.compute(x, tolerance);
        } else if (x < 0) {
            return new Result(1.0 / super.compute(-x, tolerance).x());
        } else {
            return new Result(1.0);
        }
    }
}
