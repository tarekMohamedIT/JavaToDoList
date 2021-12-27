package Application.Results;

public class SimpleResult implements Result{
    private ResultState state;
    private Exception exception;

    @Override
    public ResultState getState() {
        return state;
    }

    public void setState(ResultState state) {
        this.state = state;
    }

    @Override
    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
