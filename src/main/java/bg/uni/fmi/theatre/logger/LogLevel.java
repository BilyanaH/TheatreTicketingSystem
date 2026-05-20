package bg.uni.fmi.theatre.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogLevel {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    ERROR(3);

    private final int priority;
}
