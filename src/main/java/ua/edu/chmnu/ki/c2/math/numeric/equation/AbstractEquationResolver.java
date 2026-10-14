package ua.edu.chmnu.ki.c2.math.numeric.equation;

import ua.edu.chmnu.ki.c2.math.numeric.equation.criteria.BolzanoCauchyCriteria;

import java.util.function.DoubleUnaryOperator;

public abstract class AbstractEquationResolver implements EquationResolver {

    protected final double tolerance;

    protected final int maxIterations;

    protected int currentIteration = 0;

    public AbstractEquationResolver(double tolerance, int maxIterations) {
        this.tolerance = tolerance;
        this.maxIterations = maxIterations;
    }

    public AbstractEquationResolver() {
        this(DEFAULT_TOLERANCE, DEFAULT_MAX_ITERATIONS);
    }

    @Override
    public int getCountOfIterations() {
        return currentIteration;
    }

    protected boolean hasRootFor(DoubleUnaryOperator function, Range range) {
        return BolzanoCauchyCriteria.hasRootFor(function, range);
    }
}
