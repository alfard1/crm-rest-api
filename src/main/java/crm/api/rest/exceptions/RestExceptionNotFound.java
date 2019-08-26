package crm.api.rest.exceptions;

public class RestExceptionNotFound extends RuntimeException {

    public RestExceptionNotFound() {
    }

    public RestExceptionNotFound(String message) {
        super(message);
    }

    public RestExceptionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public RestExceptionNotFound(Throwable cause) {
        super(cause);
    }

    public RestExceptionNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
