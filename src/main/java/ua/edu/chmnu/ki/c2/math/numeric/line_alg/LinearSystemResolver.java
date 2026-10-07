package ua.edu.chmnu.ki.c2.math.numeric.line_alg;

import ua.edu.chmnu.ki.c2.math.numeric.line_alg.exception.LinearSystemException;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.ExtendedMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface LinearSystemResolver {
    Vector resolve(Matrix m, Vector b);

    default Vector resolve(ExtendedMatrix m) {
        return resolve(m.matrix(), m.vector());
    }

    default void tryToPivot(Matrix m, Vector b, int row, double tolerance) {
        // Find the pivot row
        int pivotRow = m.finPivotRowFor(row);

        // Swap the current row with the pivot row
        if (pivotRow > row) {
            m.changeRows(row, pivotRow);
            b.change(row, pivotRow);
        }

        // Check for zero pivot element
        if (Math.abs(m.get(row, row)) < tolerance) {
            throw new LinearSystemException("Matrix is singular or nearly singular");
        }
    }
}
