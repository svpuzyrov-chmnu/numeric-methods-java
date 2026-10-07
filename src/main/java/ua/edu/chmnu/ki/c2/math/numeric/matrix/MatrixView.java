package ua.edu.chmnu.ki.c2.math.numeric.matrix;

public interface MatrixView extends Matrix {

    @Override
    default void set(int i, int j, double value) {
        throw new UnsupportedOperationException("Cannot set value at position (" + i + ", " + j + ") in a lower rectangle matrix.");
    }

    @Override
    default void changeRows(int from, int to) {
        throw new UnsupportedOperationException("Changing rows is not supported for LowerRectangleMatrix");
    }

    @Override
    default void changeColumns(int from, int to) {
        throw new UnsupportedOperationException("Changing columns is not supported for LowerRectangleMatrix");
    }

    @Override
    default Matrix transpose() {
        throw new UnsupportedOperationException("Transposing is not supported for LowerRectangleMatrix");
    }

    @Override
    default Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
        throw new UnsupportedOperationException("Sub-matrix extraction is not supported for this matrix view.");
    }
}
