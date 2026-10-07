package ua.edu.chmnu.ki.c2.math.numeric.series;

import lombok.Getter;

import java.util.Locale;
import java.util.Scanner;

@Getter
public class UserInput {
    final double tolerance;
    final double x;


    private UserInput(double tolerance, double x) {
        this.tolerance = tolerance;
        this.x = x;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Scanner scanner;

        private double x = 0;

        private double tolerance = 1.e-3;

        public Builder() {
            this.scanner = new Scanner(System.in).useLocale(Locale.US);
        }

        public Builder withTolerance() {
            System.out.print("Enter tolerance:");

            this.tolerance = scanner.nextDouble();

            return this;
        }

        public Builder withX(double from, double to) {

            do {
                System.out.print("Enter x from range (" + from + ", " + to +"):");
                this.x = scanner.nextDouble();
            } while (this.x < from || this.x > to);

            return this;
        }

        public UserInput build() {
            return new UserInput(tolerance, x);
        }
    }
}
