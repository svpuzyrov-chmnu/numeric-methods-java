package ua.edu.chmnu.ki.c2.math.numeric.line_alg.factorization;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.MatrixDecorator;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.impl.RectangleArrayMatrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.operation.RectangleMatrixArrayOperation;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LUFactorizerTest {

    private static final double TOLERANCE = 1e-9;

    @ParameterizedTest
    @MethodSource("provideFactorizationCases")
    void shouldFactorizeMatrixAndReconstructPermutedInput(double[][] data, int[] expectedPivots) {
        Matrix matrix = new RectangleArrayMatrix(data);

        MatrixDecorator factorization = new LUFactorizer().factorize(matrix);

        assertArrayEquals(expectedPivots, factorization.pivotIndices());
        assertMatrixEquals(matrix, factorization);
    }

    @ParameterizedTest
    @MethodSource("provideNonSquareMatrices")
    void shouldRejectNonSquareMatrices(double[][] data) {
        Matrix matrix = new RectangleArrayMatrix(data);

        assertThrows(IllegalArgumentException.class, () -> new LUFactorizer().factorize(matrix));
    }

    private static Stream<Arguments> provideFactorizationCases() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {4.0, 1.0},
                                {2.0, 3.0}
                        },
                        new int[]{0, 1}
                ),
                Arguments.of(
                        new double[][]{
                                {4.0, 3.0},
                                {6.0, 3.0}
                        },
                        new int[]{1, 0}
                ),
                Arguments.of(
                        new double[][]{
                                {2.0, 1.0, 1.0},
                                {4.0, -6.0, 0.0},
                                {-2.0, 8.0, 2.0}
                        },
                        new int[]{1, 2, 0}
                ),
                Arguments.of(
                        new double[][]{
                                {100.0, 1.0, 2.0, 1.0},
                                {1.0, 110.0, 1.0, 2.0},
                                {2.0, 1.0, 120.0, 1.0},
                                {1.0, 2.0, 1.0, 130.0}
                        },
                        new int[]{0, 1, 2, 3}
                ),
                Arguments.of(
                        new double[][]{
                                {0.0, 2.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 4.0},
                                {3.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 5.0, 0.0}
                        },
                        new int[]{2, 0, 3, 1}
                ),
                Arguments.of(
                        new double[][]{
                                {100.0, 1.0, 2.0, 1.0, 2.0},
                                {1.0, 110.0, 1.0, 2.0, 1.0},
                                {2.0, 1.0, 120.0, 1.0, 2.0},
                                {1.0, 2.0, 1.0, 130.0, 1.0},
                                {2.0, 1.0, 2.0, 1.0, 140.0}
                        },
                        new int[]{0, 1, 2, 3, 4}
                ),
                Arguments.of(
                        new double[][]{
                                {0.0, 2.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 4.0, 0.0},
                                {3.0, 0.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 5.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 0.0, 6.0}
                        },
                        new int[]{2, 0, 3, 1, 4}
                )
        );
    }

    private static Stream<Arguments> provideNonSquareMatrices() {
        return Stream.of(
                Arguments.of((Object) new double[][]{
                        {1.0, 2.0, 3.0},
                        {4.0, 5.0, 6.0}
                }),
                Arguments.of((Object) new double[][]{
                        {1.0, 2.0},
                        {3.0, 4.0},
                        {5.0, 6.0}
                })
        );
    }

    private static void assertMatrixEquals(Matrix original, MatrixDecorator factorization) {
        var lower = factorization.getLower();
        var upper = factorization.getUpper();

        int[] inversePivots = factorization.invertPivotIndices();

        double[][] permutationData = new double[inversePivots.length][inversePivots.length];

        for (int row = 0; row < inversePivots.length; row++) {
            permutationData[row][inversePivots[row]] = 1.0;
        }

        RectangleMatrixArrayOperation operation = new RectangleMatrixArrayOperation();
        Matrix permutation = new RectangleArrayMatrix(permutationData);
        Matrix reconstructed = operation.mul(operation.mul(permutation, lower), upper);

        assertTrue(original.equals(reconstructed, TOLERANCE), "Reconstructed matrix does not match the original matrix.");
    }
}
