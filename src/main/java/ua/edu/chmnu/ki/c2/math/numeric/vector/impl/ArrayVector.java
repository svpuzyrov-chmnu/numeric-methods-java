package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.exception.VectorInvalidIndexException;

import java.util.Arrays;

public class ArrayVector implements Vector {
    private final double[] data;

    public ArrayVector(double[] data) {
        this.data = data;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= data.length) {
            throw new VectorInvalidIndexException(new IndexOutOfBoundsException("Index " + index + " is out of bounds for length " + data.length), index);
        }
    }

    @Override
    public double get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public void set(int index, double value) {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public void change(int from, int to) {
        checkIndex(from);
        checkIndex(to);

        double temp = data[from];

        data[from] = data[to];

        data[to] = temp;
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public Vector copy() {
        return new ArrayVector(Arrays.copyOf(data, data.length));
    }
}
