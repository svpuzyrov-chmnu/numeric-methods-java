package ua.edu.chmnu.ki.c2.math.numeric.matrix.writer;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.reader.RectangleMatrixArrayReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleMatrixArrayWriterTest {

    private final RectangleMatrixArrayWriter writer = new RectangleMatrixArrayWriter();
    private final RectangleMatrixArrayReader reader = new RectangleMatrixArrayReader();

    @TempDir
    Path tempDir;

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldWriteDifferentMatricesToTempFiles(double[][] data) throws IOException {
        Path file = tempDir.resolve("matrix-" + data.length + "x" + data[0].length + ".txt");

        try (OutputStream output = Files.newOutputStream(file)) {
            writer.write(new RectangleArrayMatrix(data), output);
        }

        try (InputStream input = Files.newInputStream(file)) {
            Matrix matrix = reader.readFrom(input);

            assertEquals(data.length, matrix.getRows());
            assertEquals(data[0].length, matrix.getColumns());
            for (int row = 0; row < data.length; row++) {
                for (int col = 0; col < data[0].length; col++) {
                    assertEquals(data[row][col], matrix.get(row, col), 1e-9);
                }
            }
        }
    }

    private static Stream<double[][]> provideMatrixData() {
        return Stream.of(
                new double[][]{{1.0}},
                new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}},
                new double[][]{
                        {-2.0, 0.5, 3.0},
                        {4.0, -1.5, 6.5},
                        {7.0, 8.0, -9.0}
                }
        );
    }
}