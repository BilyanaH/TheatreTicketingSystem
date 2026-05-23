package bg.uni.fmi.theatre.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "theatre")
public class TheatreProperties {
    private int reservationHoldMinutes = 15;
    private int defaultPageSize = 10;
}
