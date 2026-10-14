package ua.edu.chmnu.ki.c2.math.numeric.equation.bisect;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.equation.EquationResolvers;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Range;
import ua.edu.chmnu.ki.c2.math.numeric.equation.ResultType;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BisectEquationResolverTest {

    @ParameterizedTest
    @MethodSource("provideEquationsWithRoots")
    void shouldApproximateRoot(DoubleUnaryOperator equation, Range range, double expectedRoot, double tolerance) {
        var resolver = EquationResolvers.newBisectResolver(tolerance);

        var result = resolver.getRootBy(equation, range);

        assertEquals(ResultType.DEFINED, result.resultType());
        assertEquals(expectedRoot, result.value(), tolerance);
    }

    private static Stream<Arguments> provideEquationsWithRoots() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x - 3, new Range(0, 5), 3.0, 1e-8),
                arguments((DoubleUnaryOperator) x -> x * x - 2, new Range(0, 2), Math.sqrt(2), 1e-8),
                arguments((DoubleUnaryOperator) x -> x + 2, new Range(-3, 0), -2.0, 1e-8),
                arguments((DoubleUnaryOperator) x -> x * x - 1, new Range(0, 2), 1.0, 1e-8)
        );
    }

    @ParameterizedTest
    @MethodSource("provideEquationsWithoutRootsInRange")
    void shouldReturnUndefinedWhenRangeDoesNotBracketRoot(DoubleUnaryOperator equation, Range range) {
        var resolver = EquationResolvers.newBisectResolver();

        var result = resolver.getRootBy(equation, range);

        assertEquals(ResultType.UNDEFINED, result.resultType());
    }

    private static Stream<Arguments> provideEquationsWithoutRootsInRange() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x * x + 1, new Range(-1, 1)),
                arguments((DoubleUnaryOperator) x -> x + 2, new Range(0, 1))
        );
    }
}
