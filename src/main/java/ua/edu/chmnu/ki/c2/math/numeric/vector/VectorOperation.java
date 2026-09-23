package ua.edu.chmnu.ki.c2.math.numeric.vector;

public interface VectorOperation {

    Vector add(Vector v1, Vector v2);

    Vector sub(Vector v1, Vector v2);

    double mul(Vector v1, Vector v2);

    Vector mul(Vector other, double scalar);
}
