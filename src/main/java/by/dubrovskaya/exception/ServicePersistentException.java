package by.dubrovskaya.exception;

public class ServicePersistentException extends Exception {
    public ServicePersistentException() {
    }

    public ServicePersistentException(String message) {
        super(message);
    }

    public ServicePersistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServicePersistentException(Throwable cause) {
        super(cause);
    }

    public ServicePersistentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
