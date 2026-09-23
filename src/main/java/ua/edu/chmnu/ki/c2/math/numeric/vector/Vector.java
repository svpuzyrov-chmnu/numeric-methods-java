package ua.edu.chmnu.ki.c2.math.numeric.vector;

import ua.edu.chmnu.ki.c2.math.numeric.Copyable;

public interface Vector extends Copyable<Vector> {

    double get(int index);

    void set(int index, double value);

    void change(int from, int to);

    int size();
}
