package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

public interface SignDigitsRoundable {

    double roundTo(int signDigits);

    default double safeRoundTo(int signDigits) {
        return roundTo(signDigits);
    }
}
