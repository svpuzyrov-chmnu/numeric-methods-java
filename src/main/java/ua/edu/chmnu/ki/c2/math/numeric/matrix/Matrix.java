package ua.edu.chmnu.ki.c2.math.numeric.matrix;

import ua.edu.chmnu.ki.c2.math.numeric.Copyable;
import ua.edu.chmnu.ki.c2.math.numeric.vector.Vector;

public interface Matrix extends Copyable<Matrix> {

    double get(int i, int j);

    void set(int i, int j, double value);

    int rows();

    int cols();

    void changeRows(int from, int to);

    void changeColumns(int from, int to);

    void transpose();

    Vector getRow(int i);

    Vector getCol(int j);
}
