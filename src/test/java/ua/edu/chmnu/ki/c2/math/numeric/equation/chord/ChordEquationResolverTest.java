package ua.edu.chmnu.ki.c2.math.numeric.equation.chord;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.equation.EquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.EquationResolvers;
import ua.edu.chmnu.ki.c2.math.numeric.equation.Range;
import ua.edu.chmnu.ki.c2.math.numeric.equation.ResultType;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ChordEquationResolverTest {

    private static final double TOLERANCE = 1e-8;

    private final EquationResolver resolver = EquationResolvers.newChordResolver(TOLERANCE);

    @ParameterizedTest
    @MethodSource("provideEquationsWithRoots")
    void shouldApproximateRoot(DoubleUnaryOperator equation, Range range, double expectedRoot) {
        var result = resolver.getRootBy(equation, range);

        assertEquals(ResultType.DEFINED, result.resultType());
        assertEquals(expectedRoot, result.value(), TOLERANCE);
    }

    private static Stream<Arguments> provideEquationsWithRoots() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x - 0.25, new Range(0, 1), 0.25),
                arguments((DoubleUnaryOperator) x -> x * x - 2, new Range(1, 2), Math.sqrt(2)),
                arguments((DoubleUnaryOperator) x -> Math.exp(x) - 2, new Range(0, 1), Math.log(2)),
                arguments((DoubleUnaryOperator) x -> x + 2, new Range(-5, 0), -2.0),
                arguments((DoubleUnaryOperator) x -> -3 * x - 6, new Range(-3, 0), -2.0),
                arguments((DoubleUnaryOperator) x -> x * x - 9, new Range(0, 5), 3.0),
                arguments((DoubleUnaryOperator) x -> x * x * x - 8, new Range(1, 4), 2.0),
                arguments((DoubleUnaryOperator) x -> Math.cos(x) - x, new Range(0, 1), 0.7390851332)
        );
    }

    @ParameterizedTest
    @MethodSource("provideEndpointRoots")
    void shouldReturnRootWhenItIsAtRangeEndpoint(DoubleUnaryOperator equation, Range range, double expectedRoot) {
        var result = resolver.getRootBy(equation, range);

        assertEquals(ResultType.DEFINED, result.resultType());
        assertEquals(expectedRoot, result.value(), TOLERANCE);
    }

    private static Stream<Arguments> provideEndpointRoots() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x, new Range(0, 1), 0.0),
                arguments((DoubleUnaryOperator) x -> x - 1, new Range(0, 1), 1.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRangesWithoutRoots")
    void shouldReturnUndefinedWhenRangeDoesNotBracketRoot(DoubleUnaryOperator equation, Range range) {
        var result = resolver.getRootBy(equation, range);

        assertEquals(ResultType.UNDEFINED, result.resultType());
    }

    private static Stream<Arguments> provideRangesWithoutRoots() {
        return Stream.of(
                arguments((DoubleUnaryOperator) x -> x * x + 1, new Range(-1, 1)),
                arguments((DoubleUnaryOperator) x -> x + 2, new Range(0, 1)),
                arguments((DoubleUnaryOperator) x -> x * x + 4, new Range(2, 3)),
                arguments((DoubleUnaryOperator) x -> -x * x - 1, new Range(-2, 2))
        );
    }
}
