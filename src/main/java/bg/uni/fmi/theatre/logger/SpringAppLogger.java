package bg.uni.fmi.theatre.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringAppLogger implements  AppLogger{
    @Override
    public void trace(String message) {
        log.trace(message);
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
