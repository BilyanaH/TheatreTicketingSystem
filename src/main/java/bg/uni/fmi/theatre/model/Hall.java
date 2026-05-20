package bg.uni.fmi.theatre.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Hall {
    @NotNull
    Long id;
    String name;
}
