package ua.edu.chmnu.ki.c2.math.numeric.equation;

import ua.edu.chmnu.ki.c2.math.numeric.equation.bisect.BisectEquationResolver;
import ua.edu.chmnu.ki.c2.math.numeric.equation.chord.ChordEquationResolver;

public class EquationResolvers {
    private EquationResolvers() {}

    public static EquationResolver newBisectResolver() {
        return new BisectEquationResolver();
    }

    public static EquationResolver newBisectResolver(double tolerance) {
        return new BisectEquationResolver(tolerance, EquationResolver.DEFAULT_MAX_ITERATIONS);
    }

    public static EquationResolver newChordResolver(double tolerance) {
        return new ChordEquationResolver(tolerance, EquationResolver.DEFAULT_MAX_ITERATIONS);
    }
}
