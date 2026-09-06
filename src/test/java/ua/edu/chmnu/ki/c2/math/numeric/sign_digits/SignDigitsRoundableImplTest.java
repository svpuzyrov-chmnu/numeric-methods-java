package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class SignDigitsRoundableImplTest {

    @ParameterizedTest
    @CsvSource({
            "1329782.929910, 3, 1.33E6",
            "1329782.929910, 5, 1.3298E6",
            "0.00000348881478, 3, 3.49E-6",
            "0.00000348881478, 6, 3.48881E-6",
    })
    void shouldRoundToGivenSignDigits(double origin, int digits, double expected) {

        SignDigitsRoundable roundable = new SignDigitsRoundableImpl(origin);

        var actual = roundable.roundTo(digits);

        assertEquals(expected, actual);
    }
}