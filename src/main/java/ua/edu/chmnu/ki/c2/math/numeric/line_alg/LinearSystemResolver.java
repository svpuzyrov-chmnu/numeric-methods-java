package ua.edu.chmnu.ki.c2.math.numeric.line_alg;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface LinearSystemResolver {
    Vector resolve(Matrix m, Vector b);
}
