package View.Exceptions;

public class InvalidCommandException extends Exception{
    public InvalidCommandException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
