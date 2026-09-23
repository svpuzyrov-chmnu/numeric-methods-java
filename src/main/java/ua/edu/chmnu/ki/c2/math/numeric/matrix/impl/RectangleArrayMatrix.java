package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidIndexException;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ArrayVector;

public class RectangleArrayMatrix implements Matrix {

    private double[][] data;

    public RectangleArrayMatrix(double[][] data) {
        this.data = data;
    }

    private void checkIndex(int i, int j) {
        if (i < 0 || i >= data.length) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException("Row index " + i + " is out of bounds for length " + data.length), i, j);
        }
        if (j < 0 || j >= data[0].length) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException("Column index " + j + " is out of bounds for length " + data[0].length), i, j);
        }
    }

    @Override
    public double get(int i, int j) {
        checkIndex(i, j);
        return data[i][j];
    }

    @Override
    public void set(int i, int j, double value) {
        checkIndex(i, j);
        data[i][j] = value;
    }

    @Override
    public int rows() {
        return data.length;
    }

    @Override
    public int cols() {
        return data[0].length;
    }

    @Override
    public void changeRows(int from, int to) {
        checkIndex(from, 0);
        checkIndex(to, 0);

        double[] temp = data[from];
        data[from] = data[to];
        data[to] = temp;
    }

    @Override
    public void changeColumns(int from, int to) {
        checkIndex(0, from);
        checkIndex(0, to);

        for (int i = 0; i < data.length; i++) {
            double temp = data[i][from];
            data[i][from] = data[i][to];
            data[i][to] = temp;
        }
    }

    @Override
    public void transpose() {
        int rows = data.length;
        int cols = data[0].length;
        double[][] newData = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newData[j][i] = data[i][j];
            }
        }

        this.data = newData;
    }

    @Override
    public Vector getRow(int i) {
        checkIndex(i, 0);
        return new ArrayVector(data[i]);
    }

    @Override
    public Vector getCol(int j) {
        checkIndex(0, j);
        double[] col = new double[rows()];
        for (int i = 0; i < rows(); i++) {
            col[i] = data[i][j];
        }
        return new ArrayVector(col);
    }

    @Override
    public Matrix copy() {
        double[][] newData = new double[rows()][cols()];
        for (int i = 0; i < rows(); i++) {
            if (cols() >= 0) {
                System.arraycopy(data[i], 0, newData[i], 0, cols());
            }
        }
        return new RectangleArrayMatrix(newData);
    }
}
