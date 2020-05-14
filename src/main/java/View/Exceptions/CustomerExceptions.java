package View.Exceptions;

public class CustomerExceptions extends Exception{
    public CustomerExceptions(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
