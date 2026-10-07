package ua.edu.chmnu.ki.c2.math.numeric.matrix.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.ExtendedMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RectangleMatrixListReaderTest {
    private final MatrixReader listMatrixReader = new RectangleMatrixListReader();

    @ParameterizedTest
    @CsvSource({
            "matrix1-4x4.txt, 4, 4"
    })
    void shouldReadAsListMatrixFromFile(String resourceName, int expectedRows, int expectedCols) throws IOException {
        try (InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            Matrix matrix = listMatrixReader.readFrom(resourceStream);

            assertNotNull(matrix);

            assertEquals(expectedRows, matrix.getRows());
            assertEquals(expectedCols, matrix.getColumns());
        }
    }

    @Test
    void shouldReadExtendedMatrixFromListInput() throws IOException {
        String data = "2 -1 4.1 5\n0 3.5 -2 7\n1 0 5 8\n";
        try (InputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))) {
            ExtendedMatrix extendedMatrix = listMatrixReader.readExtendedMatrixFrom(inputStream);

            assertEquals(3, extendedMatrix.matrix().getRows());
            assertEquals(3, extendedMatrix.matrix().getColumns());
            assertEquals(2.0, extendedMatrix.matrix().get(0, 0), 1e-9);
            assertEquals(-1.0, extendedMatrix.matrix().get(0, 1), 1e-9);
            assertEquals(4.1, extendedMatrix.matrix().get(0, 2), 1e-9);
            assertEquals(0.0, extendedMatrix.matrix().get(1, 0), 1e-9);
            assertEquals(3.5, extendedMatrix.matrix().get(1, 1), 1e-9);
            assertEquals(-2.0, extendedMatrix.matrix().get(1, 2), 1e-9);
            assertEquals(1.0, extendedMatrix.matrix().get(2, 0), 1e-9);
            assertEquals(0.0, extendedMatrix.matrix().get(2, 1), 1e-9);
            assertEquals(5.0, extendedMatrix.matrix().get(2, 2), 1e-9);
            assertEquals(5.0, extendedMatrix.vector().get(0), 1e-9);
            assertEquals(7.0, extendedMatrix.vector().get(1), 1e-9);
            assertEquals(8.0, extendedMatrix.vector().get(2), 1e-9);
        }
    }
}