package ua.edu.chmnu.ki.c2.math.numeric.matrix.impl;

import ua.edu.chmnu.ki.c2.math.numeric.matrix.Matrix;
import ua.edu.chmnu.ki.c2.math.numeric.matrix.exception.MatrixInvalidIndexException;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.impl.ListVector;

import java.util.ArrayList;
import java.util.List;

public class RectangleListMatrix implements Matrix {

    private final List<List<Double>> data;

    public RectangleListMatrix(List<List<Double>> data) {
        this.data = new ArrayList<>(data.size());
        for (List<Double> row : data) {
            this.data.add(new ArrayList<>(row));
        }
    }

    public RectangleListMatrix(Matrix source) {
        this.data = new ArrayList<>(source.getRows());
        for (int i = 0; i < source.getRows(); i++) {
            List<Double> row = new ArrayList<>(source.getColumns());

            for (int j = 0; j < source.getColumns(); j++) {
                row.add(source.get(i, j));
            }

            this.data.add(row);
        }
    }

    private void checkIndex(int i, int j) {
        if (i < 0 || i >= data.size()) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException("Row index " + i + " is out of bounds for length " + data.size()), i, j);
        }
        if (j < 0 || j >= data.getFirst().size()) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException("Column index " + j + " is out of bounds for length " + data.getFirst().size()), i, j);
        }
    }

    @Override
    public double get(int i, int j) {
        checkIndex(i, j);
        return data.get(i).get(j);
    }

    @Override
    public void set(int i, int j, double value) {
        checkIndex(i, j);
        data.get(i).set(j, value);
    }

    @Override
    public int getRows() {
        return data.size();
    }

    @Override
    public int getColumns() {
        return data.getFirst().size();
    }

    @Override
    public void changeRows(int from, int to) {
        checkIndex(from, 0);
        checkIndex(to, 0);

        List<Double> temp = data.get(from);

        data.set(from, data.get(to));

        data.set(to, temp);
    }

    @Override
    public void changeColumns(int from, int to) {
        checkIndex(0, from);
        checkIndex(0, to);

        for (List<Double> row : data) {
            double temp = row.get(from);
            row.set(from, row.get(to));
            row.set(to, temp);
        }
    }

    @Override
    public Matrix transpose() {
        int rows = data.size();
        int cols = data.getFirst().size();

        List<List<Double>> result = new ArrayList<>(cols);

        for (int j = 0; j < cols; j++) {
            List<Double> row = new ArrayList<>(rows);

            for (List<Double> sourceRow : data) {
                row.add(sourceRow.get(j));
            }

            result.add(row);
        }

        return new RectangleListMatrix(result);
    }

    @Override
    public Vector getRow(int i) {
        checkIndex(i, 0);
        return new ListVector(data.get(i));
    }

    @Override
    public Vector getCol(int j) {
        checkIndex(0, j);

        List<Double> column = new ArrayList<>(data.size());

        for (int i = 0; i < getRows(); i++) {
            column.add(data.get(i).get(j));
        }
        return new ListVector(column);
    }

    @Override
    public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd) {
        checkIndex(rowStart, colStart);
        checkIndex(rowEnd, colEnd);

        List<List<Double>> subData = new ArrayList<>();

        for (int i = rowStart; i <= rowEnd; i++) {
            List<Double> row = new ArrayList<>();

            for (int j = colStart; j <= colEnd; j++) {
                row.add(data.get(i).get(j));
            }

            subData.add(row);
        }

        return new RectangleListMatrix(subData);
    }

    @Override
    public Matrix copy() {
        List<List<Double>> copy = new ArrayList<>(data.size());
        for (List<Double> row : data) {
            copy.add(new ArrayList<>(row));
        }
        return new RectangleListMatrix(copy);
    }
}
