package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidIndexException;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleListMatrixTest {

    @ParameterizedTest
    @MethodSource("provideCellValues")
    void shouldGetCellValue(int row, int col, double expected) {
        assertEquals(expected, matrix().get(row, col));
    }

    @ParameterizedTest
    @MethodSource("provideCellValues")
    void shouldSetCellValue(int row, int col, double value) {
        var matrix = matrix();

        matrix.set(row, col, value + 10.0);

        assertEquals(value + 10.0, matrix.get(row, col));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexes")
    void shouldRejectInvalidCellIndex(int row, int col) {
        var exception = assertThrows(MatrixInvalidIndexException.class, () -> matrix().get(row, col));

        assertEquals(row, exception.getRow());
        assertEquals(col, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexes")
    void shouldRejectInvalidSetIndex(int row, int col) {
        var exception = assertThrows(MatrixInvalidIndexException.class, () -> matrix().set(row, col, 10.0));

        assertEquals(row, exception.getRow());
        assertEquals(col, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReportDimensions(List<List<Double>> data) {
        var matrix = new RectangleListMatrix(copyOf(data));

        assertEquals(data.size(), matrix.getRows());
        assertEquals(data.getFirst().size(), matrix.getColumns());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapRows(List<List<Double>> data) {
        var matrix = new RectangleListMatrix(copyOf(data));
        int lastRow = data.size() - 1;

        matrix.changeRows(0, lastRow);

        List<List<Double>> expected = copyOf(data);
        var temp = expected.getFirst();
        expected.set(0, expected.get(lastRow));
        expected.set(lastRow, temp);
        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapColumns(List<List<Double>> data) {
        var matrix = new RectangleListMatrix(copyOf(data));
        int lastColumn = data.getFirst().size() - 1;

        matrix.changeColumns(0, lastColumn);

        List<List<Double>> expected = copyOf(data);
        for (int row = 0; row < data.size(); row++) {
            double temp = expected.get(row).getFirst();
            expected.get(row).set(0, expected.get(row).get(lastColumn));
            expected.get(row).set(lastColumn, temp);
        }
        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldTransposeMatrix(List<List<Double>> data) {
        var matrix = new RectangleListMatrix(copyOf(data));

        var result = matrix.transpose();

        assertEquals(matrix.getRows(), result.getColumns());
        assertEquals(matrix.getColumns(), result.getRows());

        for (int row = 0; row < data.size(); row++) {
            for (int col = 0; col < data.getFirst().size(); col++) {
                assertEquals(matrix.get(row, col), result.get(col, row));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReturnRequestedRowAndColumn(List<List<Double>> data) {
        var matrix = new RectangleListMatrix(copyOf(data));
        Vector row = matrix.getRow(1);
        Vector col = matrix.getCol(1);

        for (int index = 0; index < data.getFirst().size(); index++) {
            assertEquals(data.get(1).get(index), row.get(index));
        }
        for (int index = 0; index < data.size(); index++) {
            assertEquals(data.get(index).get(1), col.get(index));
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldCreateIndependentCopy(List<List<Double>> data) {
        Matrix original = new RectangleListMatrix(copyOf(data));
        Matrix copy = original.copy();

        copy.set(0, 0, copy.get(0, 0) + 1.0);

        assertEquals(data.getFirst().getFirst(), original.get(0, 0));
        assertEquals(data.getFirst().getFirst() + 1.0, copy.get(0, 0));
        assertEquals(data.size(), copy.getRows());
        assertEquals(data.getFirst().size(), copy.getColumns());
    }

    @ParameterizedTest
    @MethodSource("provideSubMatrixCases")
    void shouldReturnSubMatrix(List<List<Double>> data, int rowStart, int rowEnd, int colStart, int colEnd, List<List<Double>> expected) {
        var matrix = new RectangleListMatrix(copyOf(data));

        var subMatrix = matrix.subMatrix(rowStart, rowEnd, colStart, colEnd);

        assertEquals(expected.size(), subMatrix.getRows());
        assertEquals(expected.getFirst().size(), subMatrix.getColumns());
        for (int row = 0; row < expected.size(); row++) {
            for (int col = 0; col < expected.get(row).size(); col++) {
                assertEquals(expected.get(row).get(col), subMatrix.get(row, col));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSubMatrixCases")
    void shouldRejectInvalidSubMatrixRange(List<List<Double>> data, int rowStart, int rowEnd, int colStart, int colEnd) {
        var matrix = new RectangleListMatrix(copyOf(data));

        assertThrows(MatrixInvalidIndexException.class, () -> matrix.subMatrix(rowStart, rowEnd, colStart, colEnd));
    }

    private static Stream<Arguments> provideCellValues() {
        return Stream.of(
                Arguments.of(0, 0, 1.0),
                Arguments.of(0, 2, 3.0),
                Arguments.of(1, 1, 5.0),
                Arguments.of(2, 0, 7.0),
                Arguments.of(2, 2, 9.0)
        );
    }

    private static Stream<Arguments> provideInvalidIndexes() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(3, 0),
                Arguments.of(0, -1),
                Arguments.of(0, 3)
        );
    }

    private static Stream<List<List<Double>>> provideMatrixData() {
        return Stream.of(
                listOf(
                        listOf(1.0, 2.0, 3.0),
                        listOf(4.0, 5.0, 6.0),
                        listOf(7.0, 8.0, 9.0)
                ),
                listOf(
                        listOf(-1.0, 0.5, 2.5),
                        listOf(4.0, -3.0, 6.0)
                )
        );
    }

    private static Stream<Arguments> provideSubMatrixCases() {
        return Stream.of(
                Arguments.of(
                        listOf(
                                listOf(1.0, 2.0, 3.0, 4.0),
                                listOf(5.0, 6.0, 7.0, 8.0),
                                listOf(9.0, 10.0, 11.0, 12.0),
                                listOf(13.0, 14.0, 15.0, 16.0)
                        ),
                        1, 2, 1, 3,
                        listOf(
                                listOf(6.0, 7.0, 8.0),
                                listOf(10.0, 11.0, 12.0)
                        )
                ),
                Arguments.of(
                        listOf(
                                listOf(1.0, 2.0, 3.0),
                                listOf(4.0, 5.0, 6.0),
                                listOf(7.0, 8.0, 9.0)
                        ),
                        0, 2, 1, 2,
                        listOf(
                                listOf(2.0, 3.0),
                                listOf(5.0, 6.0),
                                listOf(8.0, 9.0)
                        )
                )
        );
    }

    private static Stream<Arguments> provideInvalidSubMatrixCases() {
        return Stream.of(
                Arguments.of(listOf(listOf(1.0, 2.0), listOf(3.0, 4.0)), -1, 1, 0, 1),
                Arguments.of(listOf(listOf(1.0, 2.0), listOf(3.0, 4.0)), 0, 2, 0, 1),
                Arguments.of(listOf(listOf(1.0, 2.0), listOf(3.0, 4.0)), 0, 1, -1, 1),
                Arguments.of(listOf(listOf(1.0, 2.0), listOf(3.0, 4.0)), 0, 1, 0, 3)
        );
    }

    private static RectangleListMatrix matrix() {
        return new RectangleListMatrix(listOf(
                listOf(1.0, 2.0, 3.0),
                listOf(4.0, 5.0, 6.0),
                listOf(7.0, 8.0, 9.0)
        ));
    }

    private static List<List<Double>> copyOf(List<List<Double>> data) {
        List<List<Double>> copy = new ArrayList<>();
        for (List<Double> row : data) {
            copy.add(new ArrayList<>(row));
        }
        return copy;
    }

    private static void assertMatrixEquals(List<List<Double>> expected, Matrix actual) {
        assertEquals(expected.size(), actual.getRows());
        assertEquals(expected.getFirst().size(), actual.getColumns());
        for (int row = 0; row < expected.size(); row++) {
            for (int col = 0; col < expected.get(row).size(); col++) {
                assertEquals(expected.get(row).get(col), actual.get(row, col));
            }
        }
    }

    private static List<Double> listOf(Double... values) {
        return new ArrayList<>(List.of(values));
    }

    @SafeVarargs
    private static List<List<Double>> listOf(List<Double>... rows) {
        return new ArrayList<>(List.of(rows));
    }
}