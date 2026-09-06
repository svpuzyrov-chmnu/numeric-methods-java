package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SignDigitsRoundableImpl implements SignDigitsRoundable {
    private final double source;

    public SignDigitsRoundableImpl(double source) {
        this.source = source;
    }

    @Override
    public double safeRoundTo(int signDigits) {
        return new BigDecimal(source)
                .setScale(signDigits, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public double roundTo(int signDigits) {
        if (source == 0.0) {
            return 0.0;
        }

        double absValue = Math.abs(source);

        int exponent = (int) Math.floor(Math.log10(absValue) + 1e-12);

        double factor = Math.pow(10, signDigits - exponent - 1);

        return Math.rint(source * factor) / factor;
    }
}
