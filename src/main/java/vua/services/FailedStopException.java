package vua.services;

public class FailedStopException extends ServiceException {
    public FailedStopException() {
    }

    public FailedStopException(String message) {
        super(message);
    }

    public FailedStopException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedStopException(Throwable cause) {
        super(cause);
    }
}