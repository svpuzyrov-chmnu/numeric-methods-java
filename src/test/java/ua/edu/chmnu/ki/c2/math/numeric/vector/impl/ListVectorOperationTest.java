package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListVectorOperationTest {

    private final ListVectorOperation operation = new ListVectorOperation();

    private static Stream<Arguments> provideBinaryOperationCases() {
        return Stream.of(
                Arguments.of(
                        List.of(1.0, 2.5, -3.0),
                        List.of(4.0, -0.5, 2.0),
                        List.of(5.0, 2.0, -1.0),
                        List.of(-3.0, 3.0, -5.0)
                ),
                Arguments.of(
                        List.of(1.0, 2.0, 3.0),
                        List.of(4.0, 5.0),
                        List.of(5.0, 7.0),
                        List.of(-3.0, -3.0)
                ),
                Arguments.of(
                        List.of(),
                        List.of(1.0, 2.0),
                        List.of(),
                        List.of()
                )
        );
    }

    private static Stream<Arguments> provideScalarOperationCases() {
        return Stream.of(
                Arguments.of(List.of(1.0, -2.5, 3.0), 2.0, List.of(2.0, -5.0, 6.0)),
                Arguments.of(List.of(4.0, 0.0), -0.5, List.of(-2.0, -0.0)),
                Arguments.of(List.of(), 10.0, List.of())
        );
    }

    private static Stream<Arguments> provideDotProductCases() {
        return Stream.of(
                Arguments.of(List.of(1.0, 2.5, -3.0), List.of(4.0, -0.5, 2.0), -3.25),
                Arguments.of(List.of(1.0, 2.0, 3.0), List.of(4.0, 5.0), 14.0),
                Arguments.of(List.of(), List.of(1.0, 2.0), 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideBinaryOperationCases")
    void shouldAddVectors(List<Double> first, List<Double> second, List<Double> expected, List<Double> ignored) {
        Vector actual = operation.add(vector(first), vector(second));

        assertEquals(expected, valuesOf(actual));
    }

    @ParameterizedTest
    @MethodSource("provideBinaryOperationCases")
    void shouldSubtractVectors(List<Double> first, List<Double> second, List<Double> ignored, List<Double> expected) {
        Vector actual = operation.sub(vector(first), vector(second));

        assertEquals(expected, valuesOf(actual));
    }

    @ParameterizedTest
    @MethodSource("provideDotProductCases")
    void shouldCalculateDotProduct(List<Double> first, List<Double> second, double expected) {
        assertEquals(expected, operation.mul(vector(first), vector(second)));
    }

    @ParameterizedTest
    @MethodSource("provideScalarOperationCases")
    void shouldMultiplyVectorByScalar(List<Double> values, double scalar, List<Double> expected) {
        Vector actual = operation.mul(vector(values), scalar);

        assertEquals(expected, valuesOf(actual));
    }

    private static Vector vector(List<Double> values) {
        return new ListVector(new ArrayList<>(values));
    }

    private static List<Double> valuesOf(Vector vector) {
        List<Double> values = new ArrayList<>(vector.size());
        for (int index = 0; index < vector.size(); index++) {
            values.add(vector.get(index));
        }
        return values;
    }
}