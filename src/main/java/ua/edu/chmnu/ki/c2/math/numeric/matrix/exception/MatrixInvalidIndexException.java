package ua.edu.chmnu.ki.c2.math.numeric.matrix.exception;

public class MatrixInvalidIndexException extends MatrixNumericException {
    private final int row;
    private final int col;

    public MatrixInvalidIndexException(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public MatrixInvalidIndexException(String message, int row, int col) {
        super(message);
        this.row = row;
        this.col = col;
    }

    public MatrixInvalidIndexException(String message, Throwable cause, int row, int col) {
        super(message, cause);
        this.row = row;
        this.col = col;
    }

    public MatrixInvalidIndexException(Throwable cause, int row, int col) {
        super(cause);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
