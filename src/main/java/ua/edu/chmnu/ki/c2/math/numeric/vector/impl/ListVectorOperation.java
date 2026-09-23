package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.VectorOperation;

import java.util.List;

public class ListVectorOperation implements VectorOperation {
    @Override
    public Vector add(Vector v1, Vector v2) {
        var resultSize = Math.min(v1.size(), v2.size());

        List<Double> result = new java.util.ArrayList<>(resultSize);

        for (int i = 0; i < resultSize; i++) {
            result.add(v1.get(i) + v2.get(i));
        }
        return new ListVector(result);
    }

    @Override
    public Vector sub(Vector v1, Vector v2) {
        var resultSize = Math.min(v1.size(), v2.size());

        List<Double> result = new java.util.ArrayList<>(resultSize);

        for (int i = 0; i < resultSize; i++) {
            result.add(v1.get(i) - v2.get(i));
        }
        return new ListVector(result);
    }

    @Override
    public Vector mul(Vector other, double scalar) {
        var resultSize = other.size();
        List<Double> result = new java.util.ArrayList<>(resultSize);
        for (int i = 0; i < resultSize; i++) {
            result.add(other.get(i) * scalar);
        }
        return new ListVector(result);
    }
}
