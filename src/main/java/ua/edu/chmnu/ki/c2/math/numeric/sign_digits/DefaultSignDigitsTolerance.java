package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

import ua.edu.chmnu.ki.c2.math.numeric.sign_digits.exception.InvalidTolerance;

public class DefaultSignDigitsTolerance implements SignDigitsTolerance {

    @Override
    public int getByTolerance(double tolerance) {
        tolerance = Math.abs(tolerance);

        if (tolerance > 1) {
            throw new InvalidTolerance(tolerance);
        }

        return (int) Math.ceil(Math.abs(Math.log10(tolerance))) - 1;
    }
}
