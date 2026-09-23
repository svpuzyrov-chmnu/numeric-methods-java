package ua.edu.chmnu.ki.c2.math.numeric.vector.exception;

import ua.edu.chmnu.ki.c2.math.numeric.exception.NumericException;

public class VectorNumericException extends NumericException {
    public VectorNumericException() {
        super();
    }

    public VectorNumericException(String message) {
        super(message);
    }

    public VectorNumericException(String message, Throwable cause) {
        super(message, cause);
    }

    public VectorNumericException(Throwable cause) {
        super(cause);
    }
}
