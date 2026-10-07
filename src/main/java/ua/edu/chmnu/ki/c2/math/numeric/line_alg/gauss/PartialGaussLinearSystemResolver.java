package ua.edu.chmnu.ki.c2.math.numeric.line_alg.gauss;

import ua.edu.chmnu.ki.c2.math.numeric.line_alg.LinearSystemResolver;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.exception.LinearSystemException;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

public class PartialGaussLinearSystemResolver implements LinearSystemResolver {
    @Override
    public Vector resolve(Matrix m, Vector b) {
        if (!m.isSquare()) {
            throw new LinearSystemException("Matrix must be square");
        }

        if (m.getRows() != b.size()) {
            throw new LinearSystemException("Incompatible matrix and vector dimensions");
        }

        var m1 = new RectangleArrayMatrix(m);
        var b1 = new ArrayVector(b);

        //Gaussian elimination with partial pivoting
        for (int i = 0; i < m1.getRows(); i++) {
            tryToPivot(m1, b1, i, 1e-10);

            // Eliminate the entries below the pivot
            for (int j = i + 1; j < m1.getRows(); j++) {
                double factor = -m1.get(j, i) / m1.get(i, i);
                for (int k = i; k < m1.getColumns(); k++) {
                    m1.set(j, k, m1.get(j, k) + factor * m1.get(i, k));
                }
                b1.set(j, b1.get(j) + factor * b1.get(i));
            }
        }

        // Back substitution
        Vector result = new ArrayVector(b1.size());

        for (int i = m1.getRows() - 1; i >= 0; i--) {
            double sum = 0;

            for (int j = i + 1; j < m1.getColumns(); j++) {
                sum += m1.get(i, j) * result.get(j);
            }

            result.set(i, (b1.get(i) - sum) / m1.get(i, i));
        }

        return result;
    }
}
