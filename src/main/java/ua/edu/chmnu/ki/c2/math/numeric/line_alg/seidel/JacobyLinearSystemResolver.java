package ua.edu.chmnu.ki.c2.math.numeric.line_alg.seidel;

import lombok.Getter;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.LinearSystemResolver;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.VectorOperation;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVectorOperation;

@Getter
public class JacobyLinearSystemResolver implements LinearSystemResolver {
    private final static int MAX_ITERATIONS = 10_000;

    private final double tolerance;

    private int countIterations = 0;

    public JacobyLinearSystemResolver() {
        this.tolerance = 1e-10;
    }

    public JacobyLinearSystemResolver(double tolerance) {
        this.tolerance = tolerance;
        if (!Double.isFinite(this.tolerance) || this.tolerance <= 0.0 || this.tolerance > 1.0) {
            throw new IllegalArgumentException("Tolerance must be in the range [0, 1]");
        }
    }

    @Override
    public Vector resolve(Matrix m, Vector b) {
        if (!m.isSquare()) {
            throw new IllegalArgumentException("Matrix must be square.");
        }

        if (m.rows() != b.size()) {
            throw new IllegalArgumentException("Incompatible matrix and vector dimensions.");
        }

        VectorOperation vectorOperation = new ArrayVectorOperation();

        Matrix m1 = new RectangleArrayMatrix(m);

        Vector b1 = new ArrayVector(b);

        for (int i = 0; i < m.rows(); i++) {

            tryToPivot(m1, b1, i, 1e-10);

            double factor = m1.get(i, i);

            m1.set(i, i, 0.0);

            for (int j = 0; j < m.cols(); j++) {
                if (i != j) {
                    double value = -m1.get(i, j) / factor;

                    m1.set(i, j, value);
                }
            }

            b1.set(i, b1.get(i) / factor);
        }

        Vector xCurrent = new ArrayVector(b);

        for (countIterations = 1; countIterations <= MAX_ITERATIONS; ++countIterations) {

            Vector xNext = new ArrayVector(xCurrent.size(), 0.0);

            for (int i = 0; i < b1.size(); i++) {
                var sum = 0.0;
                for (int j = 0; j < b1.size(); j++) {
                    if (i != j) {
                        sum += m1.get(i, j) * xCurrent.get(j);
                    }
                }

                xNext.set(i, sum + b1.get(i));
            }

            var difference = vectorOperation.sub(xNext, xCurrent);

            if (difference.norm() < tolerance) {
                return xCurrent;
            }

            xCurrent = xNext;
        }

        throw new IllegalStateException("Seidel method did not converge.");
    }
}
