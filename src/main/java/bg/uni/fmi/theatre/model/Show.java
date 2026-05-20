package bg.uni.fmi.theatre.model;

import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Show {
    private static final int TITLE_MAX_LENGTH = 100;
    @NonNull
    @NotBlank
    private final Long id;
    @Max(TITLE_MAX_LENGTH)
    private String title;
    private String description;
    private Genre genre;
    @Min(0)
    private int durationMinutes;
    private AgeRating ageRating;
}
