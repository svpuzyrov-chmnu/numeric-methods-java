package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import ua.edu.chmnu.ki.c2.math.numeric.series.Series;
import ua.edu.chmnu.ki.c2.math.numeric.series.UserInput;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.DefaultSignDigitsRoundable;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.DefaultSignDigitsTolerance;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.SignDigitsTolerance;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalSeriesApp {

    static final Function<Double, Double> START_EVALUATOR = x -> -(1.0 + x) * (1.0 + x);

    static final BiFunction<Double, Integer, Double> MULTIPLIER = (x, n) -> -1.0 * n / (n + 1.0) * (1.0 + x) * (1.0 + x);

    static final Function<Double, Double> SOURCE_FUNCTION = x -> -Math.log(x * x + 2 * x + 2);

    static final SignDigitsTolerance SIGN_DIGITS_TOLERANCE = new DefaultSignDigitsTolerance();

    static void main() {
        UserInput userInput = UserInput.builder()
                .withTolerance()
                .withX()
                .build();

        Series series = new DefaultFunctionalSeries(1, START_EVALUATOR, MULTIPLIER);

        processSeries(series, userInput);
    }

    private static void processSeries(Series series, UserInput userInput) {
        var result = series.compute(userInput.getX(), userInput.getTolerance());

        var signDigits = SIGN_DIGITS_TOLERANCE.getByTolerance(userInput.getTolerance());

        result = new DefaultSignDigitsRoundable(result).roundTo(signDigits);

        var sourceValue = new DefaultSignDigitsRoundable(SOURCE_FUNCTION.apply(userInput.getX())).roundTo(signDigits);
        System.out.println("Result: " + result + " with tolerance: " + userInput.getTolerance());
        System.out.println("Source function value: " + sourceValue+ " with tolerance: " + userInput.getTolerance());
        System.out.println("Count of iterations: " + series.countOfIterations());
    }
}
