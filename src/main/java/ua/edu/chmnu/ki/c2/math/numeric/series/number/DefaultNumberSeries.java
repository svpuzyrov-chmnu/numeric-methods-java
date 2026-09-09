package ua.edu.chmnu.ki.c2.math.numeric.series.number;

import ua.edu.chmnu.ki.c2.math.numeric.series.NumberSeries;

import java.util.function.Function;

public class DefaultNumberSeries implements NumberSeries {
    private final double startValue;
    private final int startNumber;
    private final Function<Integer, Double> multiplier;

    private int countIterations = 0;

    public DefaultNumberSeries(double startValue, int startNumber, Function<Integer, Double> multiplier) {
        this.startValue = startValue;
        this.startNumber = startNumber;
        this.multiplier = multiplier;
    }


    @Override
    public int countOfIterations() {
        return countIterations;
    }

    @Override
    public double compute(double tolerance) {

        this.countIterations = 0;

        var currentTerm = this.startValue;

        var result = currentTerm;

        for (var n = this.startNumber; Math.abs(currentTerm) > tolerance; ++n) {
            currentTerm *= multiplier.apply(n);

            result += currentTerm;

            ++countIterations;
        }

        return result;
    }
}
