package ua.edu.chmnu.ki.c2.math.numeric.matrix.reader;

public class MatrixReaders {
    private MatrixReaders() {
    }

    public static MatrixReader createRectangleMatrixArrayReader() {
        return new RectangleMatrixArrayReader();
    }

    public static MatrixReader createRectangleMatrixListReader() {
        return new RectangleMatrixListReader();
    }
}
