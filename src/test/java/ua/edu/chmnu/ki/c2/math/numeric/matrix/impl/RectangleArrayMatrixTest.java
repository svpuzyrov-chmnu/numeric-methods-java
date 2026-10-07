package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidIndexException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangleArrayMatrixTest {

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
        var exception = assertThrows(MatrixInvalidIndexException.class,
                () -> matrix().set(row, col, 10.0));

        assertEquals(row, exception.getRow());
        assertEquals(col, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReportDimensions(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));

        assertEquals(data.length, matrix.getRows());
        assertEquals(data[0].length, matrix.getColumns());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapRows(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));
        int lastRow = data.length - 1;

        matrix.changeRows(0, lastRow);

        double[][] expected = copyOf(data);
        var temp = expected[0];
        expected[0] = expected[lastRow];
        expected[lastRow] = temp;
        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapColumns(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));
        int lastColumn = data[0].length - 1;

        matrix.changeColumns(0, lastColumn);

        double[][] expected = copyOf(data);
        for (int row = 0; row < data.length; row++) {
            var temp = expected[row][0];
            expected[row][0] = expected[row][lastColumn];
            expected[row][lastColumn] = temp;
        }
        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldTransposeMatrix(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));

        var result = matrix.transpose();

        assertEquals(matrix.getRows(), result.getColumns());

        assertEquals(matrix.getColumns(), result.getRows());

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                assertEquals(matrix.get(row, col), result.get(col, row));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReturnRequestedRowAndColumn(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));
        Vector row = matrix.getRow(1);
        Vector col = matrix.getCol(1);

        for (int index = 0; index < data[0].length; index++) {
            assertEquals(data[1][index], row.get(index));
        }
        for (int index = 0; index < data.length; index++) {
            assertEquals(data[index][1], col.get(index));
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldCreateIndependentCopy(double[][] data) {
        Matrix original = new RectangleArrayMatrix(copyOf(data));
        Matrix copy = original.copy();

        copy.set(0, 0, copy.get(0, 0) + 1.0);

        assertEquals(data[0][0], original.get(0, 0));
        assertEquals(data[0][0] + 1.0, copy.get(0, 0));
        assertEquals(data.length, copy.getRows());
        assertEquals(data[0].length, copy.getColumns());
    }

    @ParameterizedTest
    @MethodSource("provideSubMatrixCases")
    void shouldReturnSubMatrix(Matrix matrix, int rowStart, int rowEnd, int colStart, int colEnd, double[][] expected) {
        Matrix subMatrix = matrix.subMatrix(rowStart, rowEnd, colStart, colEnd);

        assertEquals(expected.length, subMatrix.getRows());
        assertEquals(expected[0].length, subMatrix.getColumns());
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected[row].length; col++) {
                assertEquals(expected[row][col], subMatrix.get(row, col));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSubMatrixCases")
    void shouldRejectInvalidSubMatrixRange(Matrix matrix, int rowStart, int rowEnd, int colStart, int colEnd) {
        assertThrows(MatrixInvalidIndexException.class, () -> matrix.subMatrix(rowStart, rowEnd, colStart, colEnd));
    }

    private static Stream<double[][]> provideMatrixData() {
        return Stream.of(
                new double[][]{
                        {1.0, 2.0, 3.0},
                        {4.0, 5.0, 6.0},
                        {7.0, 8.0, 9.0}
                },
                new double[][]{
                        {-1.0, 0.5, 2.5},
                        {4.0, -3.0, 6.0}
                }
        );
    }

    private static Stream<Arguments> provideSubMatrixCases() {
        Matrix baseArray = new RectangleArrayMatrix(new double[][]{
                {1.0, 2.0, 3.0, 4.0},
                {5.0, 6.0, 7.0, 8.0},
                {9.0, 10.0, 11.0, 12.0},
                {13.0, 14.0, 15.0, 16.0}
        });
        Matrix baseList = new RectangleListMatrix(new java.util.ArrayList<>() {{
            add(java.util.List.of(1.0, 2.0, 3.0, 4.0));
            add(java.util.List.of(5.0, 6.0, 7.0, 8.0));
            add(java.util.List.of(9.0, 10.0, 11.0, 12.0));
            add(java.util.List.of(13.0, 14.0, 15.0, 16.0));
        }});

        return Stream.of(
                Arguments.of(baseArray, 1, 2, 1, 3, new double[][]{{6.0, 7.0, 8.0}, {10.0, 11.0, 12.0}}),
                Arguments.of(baseList, 0, 2, 2, 3, new double[][]{{3.0, 4.0}, {7.0, 8.0}, {11.0, 12.0}})
        );
    }

    private static Stream<Arguments> provideInvalidSubMatrixCases() {
        Matrix matrix = new RectangleArrayMatrix(new double[][]{
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

        return Stream.of(
                Arguments.of(matrix, -1, 0, 0, 1),
                Arguments.of(matrix, 0, 2, 0, 1),
                Arguments.of(matrix, 0, 1, -1, 1),
                Arguments.of(matrix, 0, 1, 0, 3)
        );
    }

    private static RectangleArrayMatrix matrix() {
        return new RectangleArrayMatrix(new double[][]{
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        });
    }

    private static double[][] copyOf(double[][] data) {
        double[][] copy = new double[data.length][];
        for (int row = 0; row < data.length; row++) {
            copy[row] = data[row].clone();
        }
        return copy;
    }

    private static void assertMatrixEquals(double[][] expected, Matrix actual) {
        assertEquals(expected.length, actual.getRows());
        assertEquals(expected[0].length, actual.getColumns());
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected[row].length; col++) {
                assertEquals(expected[row][col], actual.get(row, col));
            }
        }
    }
}
