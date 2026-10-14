package ua.edu.chmnu.ki.c2.math.numeric.equation;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface EquationResolver {
    double DEFAULT_TOLERANCE = 1e-9;

    int DEFAULT_MAX_ITERATIONS = 10_000;

    Result getRootBy(DoubleUnaryOperator equation, Range range);

    default int getCountOfIterations() {
        return 0;
    }
}
