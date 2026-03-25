package bg.uni.fmi.theatre.domain;

import java.util.Objects;

public record Seat(Long id, Long hallId, String rowLabel, int seatNumber, String zoneCode) {
    public Seat {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Id cannot be null");
        }

        if (seatNumber <= 0) {
            throw new IllegalArgumentException(
                    "Duration Minutes must be positive");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seat seat)) return false;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
