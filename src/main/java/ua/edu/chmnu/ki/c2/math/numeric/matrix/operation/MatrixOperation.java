package ua.edu.chmnu.ki.c2.math.numeric.matrix.operation;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface MatrixOperation {

    Matrix add(Matrix m1, Matrix m2);

    Matrix sub(Matrix m1, Matrix m2);

    Matrix mul(Matrix m1, Matrix m2);

    Matrix mul(Matrix m, double scalar);

    Vector mul(Matrix m, Vector v);
}
