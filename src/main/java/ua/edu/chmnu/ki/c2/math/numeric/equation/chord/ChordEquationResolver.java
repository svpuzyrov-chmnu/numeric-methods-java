package ua.edu.chmnu.ki.c2.math.numeric.equation.chord;

import ua.edu.chmnu.ki.c2.math.numeric.equation.AbstractEquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.EquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Range;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Result;
import ua.edu.chmnu.ki.c2.math.numeric.equation.ResultType;

import java.util.function.DoubleUnaryOperator;

public class ChordEquationResolver extends AbstractEquationResolver implements EquationResolver {

    public ChordEquationResolver(double tolerance, int maxIterations) {
        super(tolerance, maxIterations);
    }

    public ChordEquationResolver() {
        super();
    }

    @Override
    public Result getRootBy(DoubleUnaryOperator equation, Range range) {
        if (!hasRootFor(equation, range)) {
            return new Result(0.0, ResultType.UNDEFINED);
        }

        Range valueRange = range.map(equation);

        if (valueRange.left() == 0.0) {
            return new Result(range.left(), ResultType.DEFINED);
        }

        if (valueRange.right() == 0.0) {
            return new Result(range.right(), ResultType.DEFINED);
        }

        currentIteration = 0;

        while (currentIteration < maxIterations && !range.lengthIsLessOrEq(tolerance)) {
            ++currentIteration;

            var scale = Math.max(Math.abs(valueRange.left()), Math.abs(valueRange.right()));

            valueRange = valueRange.map(v -> v / scale);

            var denominator = valueRange.right() - valueRange.left();

            if (Math.abs(denominator) == 0.0) {
                return new Result(0.0, ResultType.UNDEFINED);
            }

            var x = range.left() - valueRange.left() * range.length() / denominator;

            if (range.isBorder(x)) {
                return new Result(x, ResultType.DEFINED);
            }

            var currentValue = equation.applyAsDouble(x);

            if (Math.abs(currentValue) == 0.0) {
                return new Result(x, ResultType.DEFINED);
            }

            if (Math.signum(currentValue) == Math.signum(valueRange.left())) {
                range = new Range(x, range.right());

                valueRange = new Range(currentValue, valueRange.right());
            } else {
                range = new Range(range.left(), x);

                valueRange = new Range(valueRange.left(), currentValue);
            }

            if (range.lengthIsLessOrEq(tolerance)) {
                return new Result(range.center(), ResultType.DEFINED);
            }
        }

        return new Result(0.0, ResultType.UNDEFINED);
    }
}
