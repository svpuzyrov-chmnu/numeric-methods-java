package ua.edu.chmnu.ki.c2.math.numeric.vector;

public interface VectorOperation {

    Vector add(Vector v1, Vector v2);

    Vector sub(Vector v1, Vector v2);

    default double mul(Vector v1, Vector v2) {
        double result = 0;
        for (int i = 0; i < Math.min(v1.size(), v2.size()); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    Vector mul(Vector other, double scalar);
}
