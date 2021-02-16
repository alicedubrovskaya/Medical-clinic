package by.dubrovskaya.exception;

public class IncorrectFormDataException extends Exception {
    public IncorrectFormDataException() {
    }

    public IncorrectFormDataException(String message) {
        super(message);
    }

    public IncorrectFormDataException(Throwable cause) {
        super(cause);
    }

    public IncorrectFormDataException(String param, String value) {
        super(String.format("Empty or incorrect %s parameter was found: %s", param, value));
    }
}
