package bg.uni.fmi.theatre.logger;

public interface AppLogger {
    void trace(String message);
    void debug(String message);
    void info(String message);
    void error(String message);
    void error(String message, Throwable throwable);
}
