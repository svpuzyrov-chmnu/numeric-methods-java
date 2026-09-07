package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.exception.InvalidTolerance;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSignDigitsToleranceTest {

    private final static SignDigitsTolerance SIGN_DIGITS_TOLERANCE = new DefaultSignDigitsTolerance();

    @ParameterizedTest
    @CsvSource({
            "0.000001, 5",
            "0.00075, 3",
            "1e-4, 3",
            "5e-7, 6",
    })
    void shouldSuccessGetDigitsByTolerance(double tolerance, int expected) {
        assertEquals(expected, SIGN_DIGITS_TOLERANCE.getByTolerance(tolerance));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.5453, 2.9991, 13342.838891})
    void shouldThrowErrorWhenToleranceIsIncorrect(double tolerance) {
        assertThrows(InvalidTolerance.class, () -> SIGN_DIGITS_TOLERANCE.getByTolerance(tolerance));
    }
}
