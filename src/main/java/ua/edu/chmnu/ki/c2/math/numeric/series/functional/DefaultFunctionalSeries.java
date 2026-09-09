package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultFunctionalSeries implements FunctionalSeries {
    private final int startNumber;
    private final Function<Double, Double> startEvaluator;
    private final BiFunction<Double, Integer, Double> multiplier;

    private int countIterations = 0;

    public DefaultFunctionalSeries(int startNumber, Function<Double, Double> startEvaluator, BiFunction<Double, Integer, Double> multiplier) {
        this.startNumber = startNumber;
        this.startEvaluator = startEvaluator;
        this.multiplier = multiplier;
    }

    @Override
    public int countOfIterations() {
        return countIterations;
    }

    @Override
    public double compute(double x, double tolerance) {

        this.countIterations = 0;

        var currentTerm = this.startEvaluator.apply(x);

        var result = currentTerm;

        var previous = result;

        for (var n = this.startNumber;; ++n) {
            currentTerm *= multiplier.apply(x, n);

            previous = result;

            result += currentTerm;

            if (Math.abs(result - previous) < tolerance) {
                break;
            }

            ++countIterations;
        }

        return result;
    }
}
