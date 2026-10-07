package ua.edu.chmnu.ki.c2.math.numeric.line_alg.gauss;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.gauss.exception.LinearSystemException;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PartialGaussLinearSystemResolverTest {

    private final PartialGaussLinearSystemResolver resolver = new PartialGaussLinearSystemResolver();

    private static Stream<Arguments> provideSolvableSystems() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{0.0, 2.0}, {1.0, 1.0}},
                        new double[]{4.0, 5.0},
                        new double[]{3.0, 2.0}
                ),
                Arguments.of(
                        new double[][]{{2.0, 1.0, -1.0}, {-3.0, -1.0, 2.0}, {-2.0, 1.0, 2.0}},
                        new double[]{8.0, -11.0, -3.0},
                        new double[]{2.0, 3.0, -1.0}
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
                        "Matrix must be square"
                ),
                Arguments.of(
                        new double[][]{{1.0, 0.0}, {0.0, 1.0}},
                        new double[]{1.0},
                        "Incompatible matrix and vector dimensions"
                ),
                Arguments.of(
                        new double[][]{{1.0, 2.0}, {2.0, 4.0}},
                        new double[]{3.0, 6.0},
                        "Matrix is singular or nearly singular"
                ),
                Arguments.of(
                        new double[][]{{1.0, 0.0}, {0.0, 1e-12}},
                        new double[]{1.0, 1e-12},
                        "Matrix is singular or nearly singular"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideSolvableSystems")
    void shouldResolveSolvableSystem(double[][] coefficients, double[] rightHandSide, double[] expected) {
        var matrix = new RectangleArrayMatrix(coefficients);
        var vector = new ArrayVector(rightHandSide);

        var actual = resolver.resolve(matrix, vector);

        assertArrayEquals(expected, valuesOf(actual), 1e-9);
        assertMatrixEquals(coefficients, matrix);
        assertArrayEquals(rightHandSide, valuesOf(vector));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSystems")
    void shouldRejectInvalidSystem(double[][] coefficients, double[] rightHandSide, String expectedMessage) {
        var exception = assertThrows(
                LinearSystemException.class,
                () -> resolver.resolve(
                        new RectangleArrayMatrix(coefficients),
                        new ArrayVector(rightHandSide)
                )
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    private static double[] valuesOf(Vector vector) {
        double[] values = new double[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            values[i] = vector.get(i);
        }
        return values;
    }

    private static void assertMatrixEquals(double[][] expected, Matrix actual) {
        assertEquals(expected.length, actual.getRows());
        for (int row = 0; row < expected.length; row++) {
            assertArrayEquals(expected[row], valuesOf(actual.getRow(row)));
        }
    }
}