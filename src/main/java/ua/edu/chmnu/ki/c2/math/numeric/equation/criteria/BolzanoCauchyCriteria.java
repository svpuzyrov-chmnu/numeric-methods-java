package ua.edu.chmnu.ki.c2.math.numeric.equation.criteria;

import lombok.experimental.UtilityClass;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Range;

import java.util.function.DoubleUnaryOperator;

@UtilityClass
public class BolzanoCauchyCriteria {

    public static boolean hasRootFor(DoubleUnaryOperator equation, Range range) {
        var signumRange = range.map(equation).map(Math::signum);

        return signumRange.left() != signumRange.right();
    }
}
