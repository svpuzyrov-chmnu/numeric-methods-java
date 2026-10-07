package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixDecorator;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixView;

public class MatrixDecoratorImpl implements MatrixDecorator {

    private final Matrix matrix;

    private final int[] pivotIndices;

    public MatrixDecoratorImpl(Matrix matrix, int[] pivotIndices) {
        this.matrix = matrix;
        this.pivotIndices = pivotIndices;
    }

    public MatrixDecoratorImpl(Matrix matrix) {
        this(matrix, MatrixDecorator.createPivotIndices(matrix.getRows()));
    }

    @Override
    public MatrixView getLower() {
        return new MatrixLowerViewImpl(matrix);
    }

    @Override
    public MatrixView getUpper() {
        return new MatrixUpperViewImpl(matrix);
    }

    @Override
    public int[] pivotIndices() {
        return pivotIndices;
    }
}
