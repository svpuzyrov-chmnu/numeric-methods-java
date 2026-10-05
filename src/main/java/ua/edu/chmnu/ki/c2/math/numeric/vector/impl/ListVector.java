package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.exception.VectorInvalidIndexException;

import java.util.List;

public class ListVector implements Vector {
    private final List<Double> data;

    public ListVector(List<Double> data) {
        this.data = data;
    }

    private void checkIndex(int index) {
        if (data == null) {
            throw new VectorInvalidIndexException(new IndexOutOfBoundsException("Index " + index + " is out of bounds for empty vector"), index);
        }

        if (index < 0 || index >= data.size()) {
            throw new VectorInvalidIndexException(new IndexOutOfBoundsException("Index " + index + " is out of bounds for length " + data.size()), index);
        }
    }

    @Override
    public double get(int index) {
        checkIndex(index);
        return data.get(index);
    }

    @Override
    public void set(int index, double value) {
        checkIndex(index);
        data.set(index, value);
    }

    @Override
    public void change(int from, int to) {
        checkIndex(from);
        checkIndex(to);

        double temp = data.get(from);

        data.set(from, data.get(to));

        data.set(to, temp);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public double[] toArray() {
        return data.stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public Vector copy() {
        return new ListVector(new java.util.ArrayList<>(data));
    }
}
