package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidIndexException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangleMatrixLowerViewImplTest {

    @ParameterizedTest
    @MethodSource("provideCellValues")
    void shouldExposeLowerTriangleAndZeroUpperTriangle(int row, int col, double expected) {
        var view = lowerView(squareMatrix());

        assertEquals(expected, view.get(row, col));
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDimensions")
    void shouldPreserveDimensionsAndSquareStatus(double[][] data, int rows, int cols, boolean square) {
        var view = lowerView(new RectangleArrayMatrix(data));

        assertEquals(rows, view.getRows());
        assertEquals(cols, view.getColumns());
        assertEquals(square, view.isSquare());
    }

    @ParameterizedTest
    @MethodSource("provideRowsAndColumns")
    void shouldReturnLowerTriangularRowsAndColumns(double[][] data, boolean row, int index, double[] expected) {
        var view = lowerView(new RectangleArrayMatrix(data));
        double[] actual = row ? view.getRow(index).toArray() : view.getCol(index).toArray();

        assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexActions")
    void shouldRejectInvalidIndexes(Runnable action) {
        assertThrows(MatrixInvalidIndexException.class, action::run);
    }

    @ParameterizedTest
    @MethodSource("provideUnsupportedActions")
    void shouldNotSupportMutationsOrCopying(Runnable action) {
        assertThrows(UnsupportedOperationException.class, action::run);
    }

    private static Stream<Arguments> provideCellValues() {
        return Stream.of(
                Arguments.of(0, 0, 1.0),
                Arguments.of(1, 0, -4.0),
                Arguments.of(1, 1, 1.0),
                Arguments.of(0, 1, 0.0),
                Arguments.of(0, 2, 0.0),
                Arguments.of(1, 2, 0.0)
        );
    }

    private static Stream<Arguments> provideMatrixDimensions() {
        return Stream.of(
                Arguments.of(new double[][]{
                        {1.0, 2.0, 3.0},
                        {4.0, 5.0, 6.0},
                        {7.0, 8.0, 9.0}
                }, 3, 3, true),
                Arguments.of(new double[][]{
                        {1.0, 2.0, 3.0},
                        {4.0, 5.0, 6.0}
                }, 2, 3, false)
        );
    }

    private static Stream<Arguments> provideRowsAndColumns() {
        return Stream.of(
                Arguments.of(squareData(), true, 0, new double[]{1.0, 0.0, 0.0}),
                Arguments.of(squareData(), true, 1, new double[]{-4.0, 1.0, 0.0}),
                Arguments.of(squareData(), false, 1, new double[]{0.0, 1.0, -8.0}),
                Arguments.of(new double[][]{
                        {1.0, 2.0, 3.0, 4.0},
                        {5.0, 6.0, 7.0, 8.0}
                }, true, 1, new double[]{-5.0, 1.0, 0.0, 0.0}),
                Arguments.of(new double[][]{
                        {1.0, 2.0, 3.0, 4.0},
                        {5.0, 6.0, 7.0, 8.0}
                }, false, 3, new double[]{0.0, 0.0}),
                Arguments.of(new double[][]{
                        {1.0, 2.0},
                        {3.0, 4.0},
                        {5.0, 6.0},
                        {7.0, 8.0}
                }, true, 3, new double[]{-7.0, -8.0}),
                Arguments.of(new double[][]{
                        {1.0, 2.0},
                        {3.0, 4.0},
                        {5.0, 6.0},
                        {7.0, 8.0}
                }, false, 1, new double[]{0.0, 1.0, -6.0, -8.0})
        );
    }

    private static Stream<Arguments> provideInvalidIndexActions() {
        var view = lowerView(squareMatrix());
        return Stream.of(
                Arguments.of((Runnable) () -> view.get(-1, 0)),
                Arguments.of((Runnable) () -> view.get(0, 3)),
                Arguments.of((Runnable) () -> view.getRow(-1)),
                Arguments.of((Runnable) () -> view.getRow(3)),
                Arguments.of((Runnable) () -> view.getCol(-1)),
                Arguments.of((Runnable) () -> view.getCol(3))
        );
    }

    private static Stream<Arguments> provideUnsupportedActions() {
        var view = lowerView(squareMatrix());
        return Stream.of(
                Arguments.of((Runnable) () -> view.set(1, 0, 10.0)),
                Arguments.of((Runnable) () -> view.changeRows(0, 1)),
                Arguments.of((Runnable) () -> view.changeColumns(0, 1)),
                Arguments.of((Runnable) view::transpose),
                Arguments.of((Runnable) view::copy)
        );
    }

    private static MatrixLowerViewImpl lowerView(Matrix matrix) {
        return new MatrixLowerViewImpl(matrix);
    }

    private static RectangleArrayMatrix squareMatrix() {
        return new RectangleArrayMatrix(squareData());
    }

    private static double[][] squareData() {
        return new double[][]{
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };
    }
}
