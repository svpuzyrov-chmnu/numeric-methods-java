package ua.edu.chmnu.ki.c2.math.numeric.matrix;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.view.MatrixView;

public interface MatrixDecorator {
    MatrixView getLower();

    MatrixView getUpper();

    int[] pivotIndices();

    default int[] invertPivotIndices() {
        int[] inverted = new int[pivotIndices().length];

        for (int i = 0; i < pivotIndices().length; i++) {
            inverted[pivotIndices()[i]] = i;
        }

        return inverted;
    }

    static int[] createPivotIndices(int size) {
        int[] pivotIndices = new int[size];
        for (int i = 0; i < size; i++) {
            pivotIndices[i] = i;
        }
        return pivotIndices;
    }
}
