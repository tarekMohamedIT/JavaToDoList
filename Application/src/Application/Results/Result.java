package Application.Results;

public interface Result {
    ResultState getState();
    Exception getException();
}
