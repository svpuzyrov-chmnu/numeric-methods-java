package ua.edu.chmnu.ki.c2.math.numeric.matrix;

import ua.edu.chmnu.ki.c2.math.numeric.Copyable;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface Matrix extends Copyable<Matrix> {

    double get(int i, int j);

    void set(int i, int j, double value);

    int rows();

    int cols();

    void changeRows(int from, int to);

    void changeColumns(int from, int to);

    Matrix transpose();

    Vector getRow(int i);

    Vector getCol(int j);

    default boolean isSquare() {
        return rows() == cols();
    }

    default boolean equals(Matrix other, double tolerance) {
        if (this.rows() != other.rows() || this.cols() != other.cols()) {
            return false;
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                if (Math.abs(this.get(i, j) - other.get(i, j)) > tolerance) {
                    return false;
                }
            }
        }

        return true;
    }

    default String stringView() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < rows(); i++) {
            sb.append("[");
            for (int j = 0; j < cols(); j++) {
                sb.append(get(i, j));
                if (j < cols() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < rows() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    default boolean isSymmetric() {
        if (!isSquare()) {
            return false;
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                if (get(i, j) != get(j, i)) {
                    return false;
                }
            }
        }

        return true;
    }

    default int finPivotRowFor(int row) {
        int pivotRow = row;

        for (int j = row + 1; j < rows(); j++) {
            if (Math.abs(get(j, row)) > Math.abs(get(pivotRow, row))) {
                pivotRow = j;
            }
        }

        return pivotRow;
    }
}
