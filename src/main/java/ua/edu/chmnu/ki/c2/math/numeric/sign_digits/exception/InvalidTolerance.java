package ua.edu.chmnu.ki.c2.math.numeric.sign_digits.exception;

public class InvalidTolerance extends RuntimeException{
    private final double tolerance;

    public InvalidTolerance(double tolerance) {
        super("Invalid tolerance:" + tolerance);
        this.tolerance = tolerance;
    }

    public double getTolerance() {
        return tolerance;
    }
}
