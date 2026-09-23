package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.exception.VectorInvalidIndexException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorTest {

    private static Stream<double[]> provideVectorData() {
        return Stream.of(
                new double[]{},
                new double[]{1.5},
                new double[]{-2.0, 0.0, 3.75}
        );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1.5",
            "1, 0.0",
            "2, -3.75"
    })
    void shouldGetValueAtIndex(int index, double expected) {
        var vector = new ArrayVector(new double[]{1.5, 0.0, -3.75});

        assertEquals(expected, vector.get(index));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 9.25",
            "1, -4.5",
            "2, 0.0"
    })
    void shouldSetValueAtIndex(int index, double value) {
        var vector = new ArrayVector(new double[]{1.5, 0.0, -3.75});

        vector.set(index, value);

        assertEquals(value, vector.get(index));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "1, 1",
            "2, 0"
    })
    void shouldSwapValues(int from, int to) {
        var vector = new ArrayVector(new double[]{1.5, 0.0, -3.75});

        vector.change(from, to);

        var expected = new double[]{1.5, 0.0, -3.75};
        var temp = expected[from];
        expected[from] = expected[to];
        expected[to] = temp;
        assertArrayEquals(expected, valuesOf(vector));
    }

    @ParameterizedTest
    @MethodSource("provideVectorData")
    void shouldReportVectorSize(double[] values) {
        assertEquals(values.length, new ArrayVector(values).size());
    }

    @ParameterizedTest
    @MethodSource("provideVectorData")
    void shouldCreateIndependentCopy(double[] values) {
        var original = new ArrayVector(values);
        Vector copy = original.copy();

        assertArrayEquals(values, valuesOf(copy));
        if (values.length > 0) {
            copy.set(0, copy.get(0) + 1);
            assertNotEquals(copy.get(0), original.get(0));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void shouldRejectInvalidGetIndex(int index) {
        assertInvalidIndex(() -> new ArrayVector(new double[]{1.5, 0.0, -3.75}).get(index), index);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void shouldRejectInvalidSetIndex(int index) {
        assertInvalidIndex(() -> new ArrayVector(new double[]{1.5, 0.0, -3.75}).set(index, 10.0), index);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "1, 3",
            "3, 1"
    })
    void shouldRejectInvalidChangeIndex(int from, int to) {
        assertInvalidIndex(() -> new ArrayVector(new double[]{1.5, 0.0, -3.75}).change(from, to),
                from < 0 || from >= 3 ? from : to);
    }

    private static void assertInvalidIndex(Runnable operation, int expectedIndex) {
        var exception = assertThrows(VectorInvalidIndexException.class, operation::run);
        assertEquals(expectedIndex, exception.getIndex());
    }

    private static double[] valuesOf(Vector vector) {
        var values = new double[vector.size()];
        for (int index = 0; index < vector.size(); index++) {
            values[index] = vector.get(index);
        }
        return values;
    }
}