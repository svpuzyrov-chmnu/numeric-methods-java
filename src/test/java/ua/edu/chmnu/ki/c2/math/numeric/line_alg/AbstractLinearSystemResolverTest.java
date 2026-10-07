package ua.edu.chmnu.ki.c2.math.numeric.line_alg;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractLinearSystemResolverTest {
    public static void assertResidualWithinTolerance(
            double[][] coefficients, double[] rhs, double[] solution, double tolerance) {
        for (int row = 0; row < coefficients.length; row++) {
            double value = 0.0;
            for (int col = 0; col < coefficients[row].length; col++) {
                value += coefficients[row][col] * solution[col];
            }
            assertEquals(rhs[row], value, tolerance, "Residual at row " + row);
        }
    }

    public static void assertMatrixEquals(double[][] expected, Matrix actual, double tolerance) {
        assertEquals(expected.length, actual.getRows());
        assertEquals(expected[0].length, actual.getColumns());
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected[row].length; col++) {
                assertEquals(expected[row][col], actual.get(row, col), tolerance, "Value at row " + row + ", column " + col);
            }
        }
    }

    public static double[][] copyOf(double[][] values) {
        double[][] copy = new double[values.length][];
        for (int row = 0; row < values.length; row++) {
            copy[row] = values[row].clone();
        }
        return copy;
    }
}
