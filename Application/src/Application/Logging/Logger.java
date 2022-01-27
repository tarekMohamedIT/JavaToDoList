package Application.Logging;

public interface Logger {
    void Log(String message);
    void LogException(Throwable throwable);
}
