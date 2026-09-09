package ua.edu.chmnu.ki.c2.math.numeric.series.number;

import ua.edu.chmnu.ki.c2.math.numeric.series.NumberSeries;
import ua.edu.chmnu.ki.c2.math.numeric.series.UserInput;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.DefaultSignDigitsRoundable;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.DefaultSignDigitsTolerance;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.SignDigitsRoundable;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.SignDigitsTolerance;

import java.util.function.Function;

public class NumberSeriesApp {

    static final Function<Integer, Double> MULTIPLIER = n -> (n + 1.0) / 2.0 / (2.0 * n + 1.0);

    static final SignDigitsTolerance SIGN_DIGITS_TOLERANCE = new DefaultSignDigitsTolerance();

    static void main() {
        UserInput userInput = UserInput.builder()
                .withTolerance()
                .build();

        NumberSeries series = new DefaultNumberSeries(1.0, 0, MULTIPLIER);

        processSeries(series, userInput.getTolerance());
    }

    private static void processSeries(NumberSeries series, double tolerance) {
        var result = series.compute(tolerance);

        final SignDigitsRoundable signDigitsRoundable = new DefaultSignDigitsRoundable(result);

        var signDigits = SIGN_DIGITS_TOLERANCE.getByTolerance(tolerance);

        result = signDigitsRoundable.roundTo(signDigits);

        System.out.println("Result: " + result + " with tolerance: " + tolerance);
        System.out.println("Count of iterations: " + series.countOfIterations());
    }
}
