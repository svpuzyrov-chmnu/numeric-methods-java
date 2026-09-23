package ua.edu.chmnu.ki.c2.math.numeric.vector.exception;

public class VectorInvalidIndexException extends VectorNumericException {
    private final int index;

    public VectorInvalidIndexException(int index) {
        super();
        this.index = index;
    }

    public VectorInvalidIndexException(String message, int index) {
        super(message);
        this.index = index;
    }

    public VectorInvalidIndexException(String message, Throwable cause, int index) {
        super(message, cause);
        this.index = index;
    }

    public VectorInvalidIndexException(Throwable cause, int index) {
        super(cause);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
