package vua.services;

public class FailedStartException extends ServiceException {

    public FailedStartException() {
    }

    public FailedStartException(String message) {
        super(message);
    }

    public FailedStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedStartException(Throwable cause) {
        super(cause);
    }
}