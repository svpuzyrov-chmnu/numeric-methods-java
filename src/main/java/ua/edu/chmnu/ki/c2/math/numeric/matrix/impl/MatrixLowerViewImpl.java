package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixView;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

public class MatrixLowerViewImpl implements MatrixView {
    private final Matrix matrix;

    public MatrixLowerViewImpl(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public double get(int i, int j) {
        double value = matrix.get(i, j);
        if (i < j) {
            return 0.0;
        }

        if (i == j) {
            return 1.0;
        }

        return -value;
    }

    @Override
    public int rows() {
        return matrix.rows();
    }

    @Override
    public int cols() {
        return matrix.cols();
    }

    @Override
    public Vector getRow(int i) {
        var result = new ArrayVector(cols(), 0.0);
        Vector sourceRow = matrix.getRow(i);

        for (int j = 0; j < cols() && j <= i; j++) {
            result.set(j, j == i ? 1.0 : -sourceRow.get(j));
        }

        return result;
    }

    @Override
    public Vector getCol(int j) {
        var result = new ArrayVector(rows(), 0.0);
        Vector sourceCol = matrix.getCol(j);

        for (int i = Math.max(j, 0); i < rows(); i++) {
            result.set(i, i == j ? 1.0 : -sourceCol.get(i));
        }

        return result;
    }

    @Override
    public boolean isSquare() {
        return matrix.isSquare();
    }

    @Override
    public Matrix copy() {
        throw new UnsupportedOperationException("Copying a lower rectangle matrix view is not supported.");
    }
}
