package Infrastructure.Logging;

import Application.Logging.Logger;

public class ConsoleLogger implements Logger {
    @Override
    public void Log(String message) {
        System.out.println(message);
    }

    @Override
    public void LogException(Throwable throwable) {
        System.out.println(throwable.toString());
    }
}
