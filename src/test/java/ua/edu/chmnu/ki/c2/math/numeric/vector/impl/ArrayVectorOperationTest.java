package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorOperationTest {

    private final ArrayVectorOperation operation = new ArrayVectorOperation();

    private static Stream<Arguments> binaryOperationCases() {
        return Stream.of(
                Arguments.of(
                        new double[]{1.0, 2.5, -3.0},
                        new double[]{4.0, -0.5, 2.0},
                        new double[]{5.0, 2.0, -1.0},
                        new double[]{-3.0, 3.0, -5.0}
                ),
                Arguments.of(
                        new double[]{1.0, 2.0, 3.0},
                        new double[]{4.0, 5.0},
                        new double[]{5.0, 7.0},
                        new double[]{-3.0, -3.0}
                ),
                Arguments.of(
                        new double[]{},
                        new double[]{1.0, 2.0},
                        new double[]{},
                        new double[]{}
                )
        );
    }

    private static Stream<Arguments> scalarOperationCases() {
        return Stream.of(
                Arguments.of(new double[]{1.0, -2.5, 3.0}, 2.0, new double[]{2.0, -5.0, 6.0}),
                Arguments.of(new double[]{4.0, 0.0}, -0.5, new double[]{-2.0, -0.0}),
                Arguments.of(new double[]{}, 10.0, new double[]{})
        );
    }

    private static Stream<Arguments> dotProductCases() {
        return Stream.of(
                Arguments.of(new double[]{1.0, 2.5, -3.0}, new double[]{4.0, -0.5, 2.0}, -3.25),
                Arguments.of(new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0}, 14.0),
                Arguments.of(new double[]{}, new double[]{1.0, 2.0}, 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("binaryOperationCases")
    void shouldAddVectors(double[] first, double[] second, double[] expected, double[] ignored) {
        Vector actual = operation.add(vector(first), vector(second));

        assertArrayEquals(expected, valuesOf(actual));
    }

    @ParameterizedTest
    @MethodSource("binaryOperationCases")
    void shouldSubtractVectors(double[] first, double[] second, double[] ignored, double[] expected) {
        Vector actual = operation.sub(vector(first), vector(second));

        assertArrayEquals(expected, valuesOf(actual));
    }

    @ParameterizedTest
    @MethodSource("dotProductCases")
    void shouldCalculateDotProduct(double[] first, double[] second, double expected) {
        assertEquals(expected, operation.mul(vector(first), vector(second)));
    }

    @ParameterizedTest
    @MethodSource("scalarOperationCases")
    void shouldMultiplyVectorByScalar(double[] values, double scalar, double[] expected) {
        Vector actual = operation.mul(vector(values), scalar);

        assertArrayEquals(expected, valuesOf(actual));
    }

    private static Vector vector(double[] values) {
        return new ArrayVector(values);
    }

    private static double[] valuesOf(Vector vector) {
        double[] values = new double[vector.size()];
        for (int index = 0; index < vector.size(); index++) {
            values[index] = vector.get(index);
        }
        return values;
    }
}