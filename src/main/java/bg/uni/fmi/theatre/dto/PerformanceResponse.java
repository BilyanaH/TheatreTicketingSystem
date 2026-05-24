package bg.uni.fmi.theatre.dto;

import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.vo.PerformanceStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PerformanceResponse {
    private Long id;
    private Long showId;
    private Long hallId;
    private LocalDateTime startTime;
    private PerformanceStatus status;

    public static PerformanceResponse from(Performance p) {
        PerformanceResponse r = new PerformanceResponse();
        r.id = p.getId();
        r.showId = p.getShow().getId();
        r.hallId = p.getHall().getId();
        r.startTime = p.getStartTime();
        r.status = p.getStatus();
        return r;
    }

}
