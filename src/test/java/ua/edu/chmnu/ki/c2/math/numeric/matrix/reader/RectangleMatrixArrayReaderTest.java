package ua.edu.chmnu.ki.c2.math.numeric.matrix.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.ExtendedMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RectangleMatrixArrayReaderTest {

    private final MatrixReader arrayMatrixReader = new RectangleMatrixArrayReader();

    @ParameterizedTest
    @CsvSource({
            "matrix1-4x4.txt, 4, 4"
    })
    void shouldReadAsArrayMatrixFromFile(String resourceName, int expectedRows, int expectedCols) throws IOException {
        try (InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            Matrix matrix = arrayMatrixReader.readFrom(resourceStream);

            assertNotNull(matrix);

            assertEquals(expectedRows, matrix.getRows());
            assertEquals(expectedCols, matrix.getColumns());
        }
    }

    @Test
    void shouldReadExtendedMatrixFromArrayInput() throws IOException {
        String data = "1 2 5\n3 4 7\n";

        try (InputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))) {
            ExtendedMatrix extendedMatrix = arrayMatrixReader.readExtendedMatrixFrom(inputStream);

            assertEquals(2, extendedMatrix.matrix().getRows());
            assertEquals(2, extendedMatrix.matrix().getColumns());
            assertEquals(1.0, extendedMatrix.matrix().get(0, 0), 1e-9);
            assertEquals(2.0, extendedMatrix.matrix().get(0, 1), 1e-9);
            assertEquals(3.0, extendedMatrix.matrix().get(1, 0), 1e-9);
            assertEquals(4.0, extendedMatrix.matrix().get(1, 1), 1e-9);
            assertEquals(5.0, extendedMatrix.vector().get(0), 1e-9);
            assertEquals(7.0, extendedMatrix.vector().get(1), 1e-9);
        }
    }

    @ParameterizedTest
    @MethodSource("provideExtendedMatrixFiles")
    void shouldReadExtendedMatrixFromFile(String fileName, Vector expectedVector) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            assertNotNull(inputStream);

            ExtendedMatrix extendedMatrix = arrayMatrixReader.readExtendedMatrixFrom(inputStream);

            assertEquals(expectedVector.size(), extendedMatrix.matrix().getRows());
            assertEquals(expectedVector.size(), extendedMatrix.matrix().getColumns());
            assertEquals(expectedVector.size(), extendedMatrix.vector().size());
            for (int i = 0; i < expectedVector.size(); i++) {
                assertEquals(expectedVector.get(i), extendedMatrix.vector().get(i), 1e-9);
            }
        }
    }

    private static Stream<Arguments> provideExtendedMatrixFiles() {
        return Stream.of(
                Arguments.of("linear-system-2x2.txt", new ArrayVector(new double[]{5.0, 1.0})),
                Arguments.of("linear-system-3x3.txt", new ArrayVector(new double[]{22.0, -3.0, 31.0}))
        );
    }
}