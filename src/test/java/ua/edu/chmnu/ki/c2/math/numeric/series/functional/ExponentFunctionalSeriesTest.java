package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExponentFunctionalSeriesTest {

    private final FunctionalSeries SERIES = new ExponentFunctionalSeries();

    @ParameterizedTest
    @CsvSource({
            "-10.5, 1e-5",
            "3.4, 1e-6",
            "-5.8, 1e-6",
            "-20.45, 1e-5",
            "-100.58, 1e-5",
            "120.77, 1e-6",
    })
    void shouldComputeExponential(double x, double tolerance) {
        var expected = new Result(Math.exp(x)).roundBy(tolerance);

        var actual = SERIES.compute(x, tolerance).roundBy(tolerance);

        assertEquals(expected, actual);
    }
}