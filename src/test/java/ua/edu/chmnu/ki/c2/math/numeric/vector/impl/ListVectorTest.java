package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.exception.VectorInvalidIndexException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListVectorTest {

    private static Stream<List<Double>> provideVectors() {
        return Stream.of(
                List.of(),
                List.of(1.5),
                List.of(-2.0, 0.0, 3.75)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1.5",
            "1, 0.0",
            "2, -3.75"
    })
    void shouldGetValueAtIndex(int index, double expected) {
        var vector = new ListVector(new ArrayList<>(List.of(1.5, 0.0, -3.75)));

        assertEquals(expected, vector.get(index));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 9.25",
            "1, -4.5",
            "2, 0.0"
    })
    void shouldSetValueAtIndex(int index, double value) {
        var vector = new ListVector(new ArrayList<>(List.of(1.5, 0.0, -3.75)));

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
        var vector = new ListVector(new ArrayList<>(List.of(1.5, 0.0, -3.75)));

        vector.change(from, to);

        var expected = List.of(1.5, 0.0, -3.75);
        var swapped = new ArrayList<>(expected);
        var temp = swapped.get(from);
        swapped.set(from, swapped.get(to));
        swapped.set(to, temp);
        assertEquals(swapped, valuesOf(vector));
    }

    @ParameterizedTest
    @MethodSource("provideVectors")
    void shouldReportVectorSize(List<Double> values) {
        assertEquals(values.size(), new ListVector(new ArrayList<>(values)).size());
    }

    @ParameterizedTest
    @MethodSource("provideVectors")
    void shouldCreateIndependentCopy(List<Double> values) {
        var original = new ListVector(new ArrayList<>(values));
        Vector copy = original.copy();

        assertEquals(values, valuesOf(copy));
        if (!values.isEmpty()) {
            copy.set(0, copy.get(0) + 1);
            assertNotEquals(copy.get(0), original.get(0));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void shouldRejectInvalidGetIndex(int index) {
        assertInvalidIndex(() -> vector().get(index), index);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void shouldRejectInvalidSetIndex(int index) {
        assertInvalidIndex(() -> vector().set(index, 10.0), index);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "1, 3",
            "3, 1"
    })
    void shouldRejectInvalidChangeIndex(int from, int to) {
        assertInvalidIndex(() -> vector().change(from, to),
                from < 0 || from >= 3 ? from : to);
    }

    private static ListVector vector() {
        return new ListVector(new ArrayList<>(List.of(1.5, 0.0, -3.75)));
    }

    private static void assertInvalidIndex(Runnable operation, int expectedIndex) {
        var exception = assertThrows(VectorInvalidIndexException.class, operation::run);
        assertEquals(expectedIndex, exception.getIndex());
    }

    private static List<Double> valuesOf(Vector vector) {
        var values = new ArrayList<Double>(vector.size());
        for (int index = 0; index < vector.size(); index++) {
            values.add(vector.get(index));
        }
        return values;
    }
}