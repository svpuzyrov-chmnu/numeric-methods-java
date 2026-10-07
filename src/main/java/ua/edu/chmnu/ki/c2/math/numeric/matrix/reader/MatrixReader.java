package ua.edu.chmnu.ki.c2.math.numeric.matrix.reader;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.ExtendedMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;

import java.io.IOException;
import java.io.InputStream;

public interface MatrixReader {
    Matrix readFrom(InputStream inputStream) throws IOException;

    default ExtendedMatrix readExtendedMatrixFrom(InputStream inputStream) throws IOException {
        Matrix source = readFrom(inputStream);

        return new ExtendedMatrix(
                source.subMatrix(0, source.getRows() - 1, 0, source.getColumns() - 2),
                source.getCol(source.getColumns() - 1)
        );
    }
}
