package ua.edu.chmnu.ki.c2.math.numeric.vector.impl;

import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;
import ua.edu.chmnu.ki.c2.math.numeric.vector.VectorOperation;

public class ArrayVectorOperation implements VectorOperation {
    @Override
    public Vector add(Vector v1, Vector v2) {
        double[] result = new double[Math.min(v1.size(), v2.size())];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1.get(i) + v2.get(i);
        }
        return new ArrayVector(result);
    }

    @Override
    public Vector sub(Vector v1, Vector v2) {
        double[] result = new double[Math.min(v1.size(), v2.size())];
        for (int i = 0; i < result.length; i++) {
            result[i] = v1.get(i) - v2.get(i);
        }
        return new ArrayVector(result);
    }

    @Override
    public Vector mul(Vector other, double scalar) {
        double[] result = new double[other.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = other.get(i) * scalar;
        }
        return new ArrayVector(result);
    }
}
