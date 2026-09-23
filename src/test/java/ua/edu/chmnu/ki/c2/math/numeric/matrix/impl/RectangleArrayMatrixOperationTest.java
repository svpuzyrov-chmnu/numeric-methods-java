package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidISizeException;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangleArrayMatrixOperationTest {

    private final RectangleArrayMatrixOperation operation = new RectangleArrayMatrixOperation();

    private static Stream<Arguments> provideBinaryMatrixOperations() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{1.0, 2.0}, {3.0, 4.0}},
                        new double[][]{{5.0, 6.0}, {7.0, 8.0}},
                        new double[][]{{6.0, 8.0}, {10.0, 12.0}},
                        new double[][]{{-4.0, -4.0}, {-4.0, -4.0}}
                ),
                Arguments.of(
                        new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}},
                        new double[][]{{10.0, 20.0}},
                        new double[][]{{11.0, 22.0}},
                        new double[][]{{-9.0, -18.0}}
                )
        );
    }

    private static Stream<Arguments> provideMatrixMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}},
                        new double[][]{{7.0, 8.0}, {9.0, 10.0}, {11.0, 12.0}},
                        new double[][]{{58.0, 64.0}, {139.0, 154.0}}
                ),
                Arguments.of(
                        new double[][]{{2.0, -1.0}},
                        new double[][]{{3.0}, {4.0}},
                        new double[][]{{2.0}}
                )
        );
    }

    private static Stream<Arguments> provideScalarMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{1.0, -2.0}, {3.5, 4.0}},
                        2.0,
                        new double[][]{{2.0, -4.0}, {7.0, 8.0}}
                ),
                Arguments.of(
                        new double[][]{{4.0, 0.0}},
                        -0.5,
                        new double[][]{{-2.0, -0.0}}
                )
        );
    }

    private static Stream<Arguments> provideVectorMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}},
                        new double[]{2.0, -1.0, 0.5},
                        new double[]{1.5, 6.0}
                ),
                Arguments.of(
                        new double[][]{{3.0, 4.0}},
                        new double[]{2.0, 5.0},
                        new double[]{26.0}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideBinaryMatrixOperations")
    void shouldAddMatrices(double[][] first, double[][] second, double[][] expected, double[][] ignored) {
        assertMatrixEquals(expected, operation.add(matrix(first), matrix(second)));
    }

    @ParameterizedTest
    @MethodSource("provideBinaryMatrixOperations")
    void shouldSubtractMatrices(double[][] first, double[][] second, double[][] ignored, double[][] expected) {
        assertMatrixEquals(expected, operation.sub(matrix(first), matrix(second)));
    }

    @ParameterizedTest
    @MethodSource("provideMatrixMultiplication")
    void shouldMultiplyMatrices(double[][] first, double[][] second, double[][] expected) {
        assertMatrixEquals(expected, operation.mul(matrix(first), matrix(second)));
    }

    @ParameterizedTest
    @MethodSource("provideScalarMultiplication")
    void shouldMultiplyMatrixByScalar(double[][] values, double scalar, double[][] expected) {
        assertMatrixEquals(expected, operation.mul(matrix(values), scalar));
    }

    @ParameterizedTest
    @MethodSource("provideVectorMultiplication")
    void shouldMultiplyMatrixByVector(double[][] values, double[] vectorValues, double[] expected) {
        Vector actual = operation.mul(matrix(values), new ArrayVector(vectorValues));

        assertArrayEquals(expected, valuesOf(actual));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMatrixMultiplicationSizes")
    void shouldRejectInvalidMatrixMultiplicationSizes(double[][] first, double[][] second) {
        var exception = assertThrows(MatrixInvalidISizeException.class,
                () -> operation.mul(matrix(first), matrix(second)));

        assertEquals(first[0].length, exception.getRow());
        assertEquals(second.length, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVectorMultiplicationSizes")
    void shouldRejectInvalidVectorMultiplicationSizes(double[][] values, double[] vectorValues) {
        var exception = assertThrows(MatrixInvalidISizeException.class,
                () -> operation.mul(matrix(values), new ArrayVector(vectorValues)));

        assertEquals(values[0].length, exception.getRow());
        assertEquals(vectorValues.length, exception.getCol());
    }

    private static Stream<Arguments> provideInvalidMatrixMultiplicationSizes() {
        return Stream.of(
                Arguments.of(new double[][]{{1.0, 2.0}}, new double[][]{{1.0}, {2.0}, {3.0}}),
                Arguments.of(new double[][]{{1.0}, {2.0}}, new double[][]{{1.0, 2.0}, {3.0, 4.0}})
        );
    }

    private static Stream<Arguments> provideInvalidVectorMultiplicationSizes() {
        return Stream.of(
                Arguments.of(new double[][]{{1.0, 2.0, 3.0}}, new double[]{1.0, 2.0}),
                Arguments.of(new double[][]{{1.0}, {2.0}}, new double[]{1.0, 2.0})
        );
    }

    private static Matrix matrix(double[][] values) {
        return new RectangleArrayMatrix(copyOf(values));
    }

    private static double[][] copyOf(double[][] values) {
        double[][] copy = new double[values.length][];
        for (int row = 0; row < values.length; row++) {
            copy[row] = values[row].clone();
        }
        return copy;
    }

    private static double[] valuesOf(Vector vector) {
        double[] values = new double[vector.size()];
        for (int index = 0; index < vector.size(); index++) {
            values[index] = vector.get(index);
        }
        return values;
    }

    private static void assertMatrixEquals(double[][] expected, Matrix actual) {
        assertEquals(expected.length, actual.rows());
        assertEquals(expected[0].length, actual.cols());
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected[row].length; col++) {
                assertEquals(expected[row][col], actual.get(row, col));
            }
        }
    }
}