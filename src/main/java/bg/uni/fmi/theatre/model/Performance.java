package bg.uni.fmi.theatre.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record Performance(Long id, Long showId, Long hallId, LocalDateTime startTime) {
    public Performance {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Performance performance)) return false;
        return Objects.equals(id, performance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
