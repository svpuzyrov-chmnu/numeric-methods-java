package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixOperation;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidISizeException;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

public class RectangleArrayMatrixOperation implements MatrixOperation {
    @Override
    public Matrix add(Matrix m1, Matrix m2) {
        var result = new double[Math.min(m1.rows(), m2.rows())][Math.min(m1.cols(), m2.cols())];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = m1.get(i, j) + m2.get(i, j);
            }
        }
        return new RectangleArrayMatrix(result);
    }

    @Override
    public Matrix sub(Matrix m1, Matrix m2) {
        var result = new double[Math.min(m1.rows(), m2.rows())][Math.min(m1.cols(), m2.cols())];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = m1.get(i, j) - m2.get(i, j);
            }
        }
        return new RectangleArrayMatrix(result);
    }

    @Override
    public Matrix mul(Matrix m1, Matrix m2) {
        if (m1.cols() != m2.rows()) {
            throw new MatrixInvalidISizeException("First Matrix columns must be equal to second matrix rows for multiplication. First matrix columns: " + m1.cols() + ", Second Matrix rows: " + m2.rows(), m1.cols(), m2.rows());
        }
        var result = new double[m1.rows()][m2.cols()];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                double sum = 0;
                for (int k = 0; k < m1.cols(); k++) {
                    sum += m1.get(i, k) * m2.get(k, j);
                }
                result[i][j] = sum;
            }
        }
        return new RectangleArrayMatrix(result);
    }

    @Override
    public Matrix mul(Matrix m, double scalar) {
        var result = new double[m.rows()][m.cols()];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = m.get(i, j) * scalar;
            }
        }
        return new RectangleArrayMatrix(result);
    }

    @Override
    public Vector mul(Matrix m, Vector v) {
        if (m.cols() != v.size()) {
            throw new MatrixInvalidISizeException("Matrix columns must be equal to vector size for multiplication. Matrix columns: " + m.cols() + ", Vector size: " + v.size(), m.cols(), v.size());
        }

        double[] result = new double[m.rows()];
        for (int i = 0; i < m.rows(); i++) {
            double sum = 0;
            for (int j = 0; j < m.cols(); j++) {
                sum += m.get(i, j) * v.get(j);
            }
            result[i] = sum;
        }
        return new ArrayVector(result);
    }
}
