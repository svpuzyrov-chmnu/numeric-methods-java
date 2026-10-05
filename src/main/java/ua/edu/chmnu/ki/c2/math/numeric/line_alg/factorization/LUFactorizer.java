package ua.edu.chmnu.ki.c2.math.numeric.line_alg.factorization;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixDecorator;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.MatrixDecoratorImpl;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;

public class LUFactorizer implements Factorizer {

    @Override
    public MatrixDecorator factorize(Matrix matrix) {
        if (!matrix.isSquare()) {
            throw new IllegalArgumentException("Matrix must be square for LU factorization.");
        }

        var target = new RectangleArrayMatrix(matrix);

        int[] pivotIndices = MatrixDecorator.createPivotIndices(matrix.rows());

        for (int i = 0; i < target.rows(); i++) {
            // Pivoting
            int maxRow = i;
            for (int k = i + 1; k < target.rows(); k++) {
                if (Math.abs(target.get(k, i)) > Math.abs(target.get(maxRow, i))) {
                    maxRow = k;
                }
            }
            if (maxRow != i) {
                // Swap rows in the matrix
                target.changeRows(i, maxRow);

                // Record the pivot
                int tempIndex = pivotIndices[i];
                pivotIndices[i] = pivotIndices[maxRow];
                pivotIndices[maxRow] = tempIndex;
            }

            // LU Decomposition
            for (int j = i + 1; j < target.rows(); j++) {
                double factor = -target.get(j, i) / target.get(i, i);
                target.set(j, i, factor);
                for (int k = i + 1; k < target.cols(); k++) {
                    target.set(j, k, target.get(j, k) + factor * target.get(i, k));
                }
            }
        }

        return new MatrixDecoratorImpl(target, pivotIndices);
    }
}
