package ua.edu.chmnu.ki.c2.math.numeric.matrix.writer;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;

import java.io.IOException;
import java.io.OutputStream;

public interface MatrixWriter {
    void write(Matrix matrix, OutputStream outputStream) throws IOException;
}
