package ua.edu.chmnu.ki.c2.math.numeric.sign_digits;

public class SignDigitsRoundApp {
    static void main() {
        double bigNum = 2478389819.4939101;

        double smallNum = 0.0000239929001;

        SignDigitsRoundable[] roundables = {
                new DefaultSignDigitsRoundable(bigNum),
                new DefaultSignDigitsRoundable(smallNum),
        };

        System.out.println("===========================");

        for (int signDigits = 2; signDigits <= 9; ++signDigits) {

            System.out.println("Signum digits: " + signDigits);

            for (var r: roundables) {
                System.out.println(r.roundTo(signDigits));
            }

            System.out.println("===========================");
        }
    }
}
