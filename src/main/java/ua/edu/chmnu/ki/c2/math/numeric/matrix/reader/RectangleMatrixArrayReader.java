package ua.edu.chmnu.ki.c2.math.numeric.matrix.reader;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RectangleMatrixArrayReader implements MatrixReader {

    @Override
    public Matrix readFrom(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {

            List<String> rows = new ArrayList<>();

            while (scanner.hasNextLine()) {
                rows.add(scanner.nextLine());
            }

            double[][] data = rows.stream()
                    .filter(line -> line != null && !line.isBlank())
                    .map(line ->
                            Arrays.stream(line.split("\\s+")).mapToDouble(Double::parseDouble).toArray()
                    )
                    .toArray(double[][]::new);

            return new RectangleArrayMatrix(data);
        }
    }

}
