package ua.edu.chmnu.ki.c2.math.numeric.equation.bisect;

import ua.edu.chmnu.ki.c2.math.numeric.equation.AbstractEquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.EquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Range;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Result;
import ua.edu.chmnu.ki.c2.math.numeric.equation.ResultType;

import java.util.function.DoubleUnaryOperator;

public class BisectEquationResolver extends AbstractEquationResolver implements EquationResolver {
    public BisectEquationResolver(double tolerance, int maxIterations) {
        super(tolerance, maxIterations);
    }

    public BisectEquationResolver() {
        super();
    }

    @Override
    public Result getRootBy(DoubleUnaryOperator equation, Range range) {
        if (!hasRootFor(equation, range)) {
            return new Result(0.0, ResultType.UNDEFINED);
        }

        currentIteration = 0;
        while (currentIteration < maxIterations && !range.lengthIsLessOrEq(tolerance)) {
            ++currentIteration;
            var leftCenter = range.centerLeft();

            var rightCenter = range.centerRight();

            if (hasRootFor(equation, leftCenter)) {
                range = leftCenter;
            } else if (hasRootFor(equation, rightCenter)) {
                range = rightCenter;
            } else {
                return new Result(0.0, ResultType.UNDEFINED);
            }
        }

        return new Result(range.center(), ResultType.DEFINED);
    }

}
