package ua.edu.chmnu.ki.c2.math.numeric.series.functional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.chmnu.ki.c2.math.numeric.series.FunctionalSeries;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FastLogarithmFunctionalSeriesTest {

    private final FunctionalSeries SERIES = new FastLogarithmFunctionalSeries();

    @ParameterizedTest
    @CsvSource({
            "2.5, 1e-7",
            "0.5, 1e-8",
            "15.4, 1e-4",
            "255.56, 1e-7",
            "10049.5556, 1e-9",
    })
    void shouldComputeLogarithmWithTolerance(double x, double tolerance) {
        var expected = Math.log(x);

        var actual = SERIES.compute(x, tolerance);

        System.out.println(expected);
        System.out.println(actual);
        assertTrue(Math.abs(expected - actual) <= tolerance);
    }
}