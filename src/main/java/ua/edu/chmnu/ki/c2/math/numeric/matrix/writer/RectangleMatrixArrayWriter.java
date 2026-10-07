package ua.edu.chmnu.ki.c2.math.numeric.matrix.writer;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;

import java.io.IOException;
import java.io.OutputStream;

public class RectangleMatrixArrayWriter implements MatrixWriter {
    @Override
    public void write(Matrix matrix, OutputStream outputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                sb.append(matrix.get(i, j)).append(" ");
            }
            sb.append("\n");
        }
        outputStream.write(sb.toString().getBytes());
    }
}
