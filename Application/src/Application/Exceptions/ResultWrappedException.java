package Application.Exceptions;

public class ResultWrappedException extends RuntimeException {
    private final Throwable throwable;

    public ResultWrappedException(Throwable throwable) {
        super("A result's operation threw an exception, use getCaughtException() to get the exception");
        this.throwable = throwable;
    }

    public Throwable getCaughtException() {
        return throwable;
    }
}
