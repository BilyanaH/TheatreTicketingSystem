package bg.uni.fmi.theatre.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Seat {
    @NotNull
    Long id;
    Long hallId;
    String rowLabel;
    @Size(min = 0)
    int seatNumber;
    String zoneCode;
}
