package ua.edu.chmnu.ki.c2.math.numeric.equation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.equation.criteria.BolzanoCauchyCriteria;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BolzanoCauchyCriteriaTest {

    @ParameterizedTest
    @MethodSource("provideRootCriteriaCases")
    void shouldDetermineWhetherFunctionValuesAtRangeEndsHaveDifferentSigns(DoubleUnaryOperator equation, Range range, boolean expected) {
        assertEquals(expected, BolzanoCauchyCriteria.hasRootFor(equation, range));
    }

    private static Stream<Arguments> provideRootCriteriaCases() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x, new Range(-1, 1), true),
                arguments((DoubleUnaryOperator) x -> -x, new Range(-1, 1), true),
                arguments((DoubleUnaryOperator) x -> x * x + 1, new Range(-2, 2), false),
                arguments((DoubleUnaryOperator) x -> -x * x - 1, new Range(-2, 2), false),
                arguments((DoubleUnaryOperator) x -> x * x, new Range(0, 2), true),
                arguments((DoubleUnaryOperator) x -> x * x - 1, new Range(-2, -1), true)
        );
    }
}
