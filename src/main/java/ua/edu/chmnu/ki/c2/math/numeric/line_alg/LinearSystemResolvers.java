package ua.edu.chmnu.ki.c2.math.numeric.line_alg;

import ua.edu.chmnu.ki.c2.math.numeric.line_alg.gauss.PartialGaussLinearSystemResolver;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.jacoby.JacobyLinearSystemResolver;
import ua.edu.chmnu.ki.c2.math.numeric.line_alg.seidel.SeidelLinearSystemResolver;

public class LinearSystemResolvers {

    private LinearSystemResolvers() {
    }

    public static LinearSystemResolver newPatrialGaussResolver() {
        return new PartialGaussLinearSystemResolver();
    }

    public static LinearSystemResolver newJacobyResolver(double tolerance) {
        return new JacobyLinearSystemResolver(tolerance);
    }

    public static LinearSystemResolver newSeidelResolver(double tolerance) {
        return new SeidelLinearSystemResolver(tolerance);
    }
}
