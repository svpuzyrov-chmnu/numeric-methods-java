package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FastLogarithmFunctionalSeries implements FunctionalSeries {
    private final int startNumber;
    private final Function<Double, Double> startEvaluator;
    private final BiFunction<Double, Integer, Double> multiplier;

    private int countIterations = 0;

    public FastLogarithmFunctionalSeries() {
        this(0);
    }
    public FastLogarithmFunctionalSeries(int startNumber) {
        this.startNumber = startNumber;
        this.startEvaluator = x -> x;
        this.multiplier = (x, n) -> x*x*(2.0 * n + 1.0)/(2.0 * n + 3.0);
    }


    @Override
    public int countOfIterations() {
        return countIterations;
    }

    @Override
    public double compute(double x, double tolerance) {

        this.countIterations = 0;

        var additionalDivider = 1.0;

        if (x > 1) {
            additionalDivider = (Math.ceil(Math.abs(Math.log10(x)))) - 1;
            if (additionalDivider >= 1.0) {
                tolerance = tolerance/Math.pow(10.0, (int) additionalDivider);
            }
        }

        x = transformTo(x);

        var currentTerm = this.startEvaluator.apply(x);

        var result = 2* currentTerm;

        for (var n = this.startNumber;; ++n) {
            currentTerm *= multiplier.apply(x, n);

            var previous = result;

            result += 2* currentTerm;

            var diff = Math.abs(result - previous);

            if (diff < tolerance) {
                break;
            }

            ++countIterations;
        }

        return result;
    }

    private double transformTo(double x) {

        return (x - 1)/(x + 1);
    }
}
