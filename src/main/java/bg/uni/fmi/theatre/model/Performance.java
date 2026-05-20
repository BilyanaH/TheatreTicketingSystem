package bg.uni.fmi.theatre.model;

import bg.uni.fmi.theatre.vo.PerformanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class Performance {
    @NotNull
    Long id;
    Long showId;
    Long hallId;
    LocalDateTime startTime;
    private PerformanceStatus status;

}
