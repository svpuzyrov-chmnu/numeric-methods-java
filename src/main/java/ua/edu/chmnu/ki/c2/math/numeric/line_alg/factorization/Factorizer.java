package ua.edu.chmnu.ki.c2.math.numeric.line_alg.factorization;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixDecorator;

public interface Factorizer {
    MatrixDecorator factorize(Matrix matrix);
}
