package ua.edu.chmnu.ki.c2.math.numeric.matrix.exception;

import ua.edu.chmnu.ki.c2.math.numeric.exception.NumericException;

public class MatrixNumericException extends NumericException {
    public MatrixNumericException() {
        super();
    }

    public MatrixNumericException(String message) {
        super(message);
    }

    public MatrixNumericException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixNumericException(Throwable cause) {
        super(cause);
    }
}
