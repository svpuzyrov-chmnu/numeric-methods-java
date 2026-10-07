package ua.edu.chmnu.ki.c2.math.numeric.line_alg.jacoby;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.AbstractLinearSystemResolverTest;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.exception.LinearSystemException;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JacobyLinearSystemResolverTest extends AbstractLinearSystemResolverTest {

    private static final double TOLERANCE = 1e-8;

    private static Stream<Arguments> provideConvergentSystems() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{4.0, 0.0}, {0.0, 2.0}},
                        new double[]{8.0, 6.0},
                        new double[]{2.0, 3.0}
                ),
                Arguments.of(
                        new double[][]{{4.0, 1.0}, {2.0, 3.0}},
                        new double[]{6.0, 8.0},
                        new double[]{1.0, 2.0}
                ),
                Arguments.of(
                        new double[][]{
                                {10.0, 1.0, 1.0},
                                {2.0, 10.0, 1.0},
                                {1.0, 1.0, 10.0}
                        },
                        new double[]{22.0, -3.0, 31.0},
                        new double[]{2.0, -1.0, 3.0}
                ),
                Arguments.of(
                        new double[][]{{0.0, 2.0}, {1.0, 1.0}},
                        new double[]{4.0, 5.0},
                        new double[]{3.0, 2.0}
                ),
                Arguments.of(
                        new double[][]{{4.0}},
                        new double[]{10.0},
                        new double[]{2.5}
                )
        );
    }

    private static Stream<Arguments> provideInvalidSystems() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}},
                        new double[]{1.0, 2.0},
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        new double[][]{{1.0, 0.0}, {0.0, 1.0}},
                        new double[]{1.0},
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        new double[][]{{1.0, 2.0}, {0.0, 0.0}},
                        new double[]{1.0, 0.0},
                        LinearSystemException.class
                ),
                Arguments.of(
                        new double[][]{{1.0, 0.0}, {0.0, 1e-12}},
                        new double[]{1.0, 1e-12},
                        LinearSystemException.class
                )
        );
    }

    private static Stream<Double> provideInvalidTolerances() {
        return Stream.of(-1.0, 0.0, 1.01, Double.NaN, Double.POSITIVE_INFINITY);
    }

    private static Stream<Double> provideTolerancesInRange() {
        return Stream.of(1e-10, 1e-6, 0.5, 1.0);
    }

    private static Stream<Arguments> provideNonConvergentSystems() {
        return Stream.of(Arguments.of(
                new double[][]{{1.0, 2.0}, {0.6, 1.0}},
                new double[]{3.0, 1.0}
        ));
    }

    @ParameterizedTest
    @MethodSource("provideConvergentSystems")
    void shouldResolveConvergentSystems(double[][] coefficients, double[] rhs, double[] expected) {
        Matrix matrix = new RectangleArrayMatrix(copyOf(coefficients));
        Vector vector = new ArrayVector(rhs.clone());

        Vector actual = new JacobyLinearSystemResolver(TOLERANCE).resolve(matrix, vector);

        assertArrayEquals(expected, actual.toArray(), TOLERANCE);
        assertResidualWithinTolerance(coefficients, rhs, actual.toArray(), 1e-6);
        assertMatrixEquals(coefficients, matrix, 1e-6);
        assertArrayEquals(rhs, vector.toArray());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSystems")
    void shouldRejectInvalidSystems(
            double[][] coefficients, double[] rhs, Class<? extends RuntimeException> exceptionType) {
        assertThrows(exceptionType, () -> new JacobyLinearSystemResolver(TOLERANCE).resolve(
                new RectangleArrayMatrix(copyOf(coefficients)),
                new ArrayVector(rhs.clone())
        ));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTolerances")
    void shouldRejectInvalidTolerances(double tolerance) {
        assertThrows(IllegalArgumentException.class, () -> new JacobyLinearSystemResolver(tolerance));
    }

    @ParameterizedTest
    @MethodSource("provideTolerancesInRange")
    void shouldPreserveConfiguredTolerance(double tolerance) {
        assertEquals(tolerance, new JacobyLinearSystemResolver(tolerance).getTolerance());
    }

    @ParameterizedTest
    @MethodSource("provideNonConvergentSystems")
    void shouldReportNonConvergence(double[][] coefficients, double[] rhs) {
        assertThrows(IllegalStateException.class, () -> new JacobyLinearSystemResolver(TOLERANCE).resolve(
                new RectangleArrayMatrix(copyOf(coefficients)),
                new ArrayVector(rhs.clone())
        ));
    }

    @ParameterizedTest
    @MethodSource("provideConvergentSystems")
    void shouldRecordIterationsAndSupportRepeatedCalls(double[][] coefficients, double[] rhs, double[] expected) {
        var resolver = new JacobyLinearSystemResolver(TOLERANCE);
        Vector first = resolver.resolve(
                new RectangleArrayMatrix(copyOf(coefficients)),
                new ArrayVector(rhs.clone())
        );

        int firstCallIterations = resolver.getCountIterations();

        Vector second = resolver.resolve(
                new RectangleArrayMatrix(copyOf(coefficients)),
                new ArrayVector(rhs.clone())
        );

        assertArrayEquals(expected, first.toArray(), TOLERANCE);
        assertArrayEquals(expected, second.toArray(), TOLERANCE);
        assertTrue(firstCallIterations > 0);
        assertEquals(firstCallIterations, resolver.getCountIterations());
    }
}
