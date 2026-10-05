package ua.edu.chmnu.ki.c2.math.numeric.vector;

import ua.edu.chmnu.ki.c2.math.numeric.Copyable;

public interface Vector extends Copyable<Vector> {

    double get(int index);

    void set(int index, double value);

    void change(int from, int to);

    int size();

    double[] toArray();

    default boolean equals(Vector other, double tolerance) {
        if (this.size() != other.size()) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            if (Math.abs(this.get(i) - other.get(i)) > tolerance) {
                return false;
            }
        }

        return true;
    }

    default String stringView() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i));
            if (i < size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    default double norm() {
        double sum = 0.0;
        for (int i = 0; i < size(); i++) {
            sum += get(i) * get(i);
        }
        return Math.sqrt(sum);
    }
}
