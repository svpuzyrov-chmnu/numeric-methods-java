package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixView;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

public class MatrixUpperViewImpl implements MatrixView {
    private final Matrix matrix;

    public MatrixUpperViewImpl(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public double get(int i, int j) {
        double value = matrix.get(i, j);
        if (i > j) {
            return 0.0;
        }

        return value;
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

        for (int j = Math.max(i, 0); j < cols(); j++) {
            result.set(j, sourceRow.get(j));
        }

        return result;
    }

    @Override
    public Vector getCol(int j) {
        var result = new ArrayVector(rows(), 0.0);
        Vector sourceCol = matrix.getCol(j);

        for (int i = 0; i < rows() && i <= j; i++) {
            result.set(i, sourceCol.get(i));
        }

        return result;
    }

    @Override
    public boolean isSquare() {
        return matrix.isSquare();
    }

    @Override
    public Matrix copy() {
        throw new UnsupportedOperationException("Copying an upper rectangle matrix view is not supported.");
    }
}
