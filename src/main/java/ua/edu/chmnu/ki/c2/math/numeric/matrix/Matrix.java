package ua.edu.chmnu.ki.c2.math.numeric.matrix;

import ua.edu.chmnu.ki.c2.math.numeric.Copyable;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface Matrix extends Copyable<Matrix> {

    double get(int i, int j);

    void set(int i, int j, double value);

    int getRows();

    int getColumns();

    void changeRows(int from, int to);

    void changeColumns(int from, int to);

    Matrix transpose();

    Vector getRow(int i);

    Vector getCol(int j);

    Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd);

    default boolean isSquare() {
        return getRows() == getColumns();
    }

    default boolean equals(Matrix other, double tolerance) {
        if (this.getRows() != other.getRows() || this.getColumns() != other.getColumns()) {
            return false;
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
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
        for (int i = 0; i < getRows(); i++) {
            sb.append("[");
            for (int j = 0; j < getColumns(); j++) {
                sb.append(get(i, j));
                if (j < getColumns() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < getRows() - 1) {
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

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (get(i, j) != get(j, i)) {
                    return false;
                }
            }
        }

        return true;
    }

    default int finPivotRowFor(int row) {
        int pivotRow = row;

        for (int j = row + 1; j < getRows(); j++) {
            if (Math.abs(get(j, row)) > Math.abs(get(pivotRow, row))) {
                pivotRow = j;
            }
        }

        return pivotRow;
    }
}
