package bg.uni.fmi.theatre.model;

import java.util.Objects;

public record Show(Long id, String title, String description, Genre genre, int durationMinutes, AgeRating ageRating) {

    private static final int TITLE_MAX_LENGTH = 100;

    public Show {
        if (id == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Id cannot be null");
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Title length cannot be more than " + TITLE_MAX_LENGTH);
        }

        if (durationMinutes <= 0) {
            throw new IllegalArgumentException(
                    "Duration Minutes must be positive");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Show show)) return false;
        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
