package View.Exceptions;

public class CancelException extends Exception {
    public CancelException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
