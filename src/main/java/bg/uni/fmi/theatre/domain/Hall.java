package bg.uni.fmi.theatre.domain;

import java.util.Objects;

public record Hall(Long id, String name) {
    public Hall {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hall hall)) return false;
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
