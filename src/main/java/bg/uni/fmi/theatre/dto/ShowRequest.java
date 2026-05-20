package bg.uni.fmi.theatre.dto;

import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;
    private String description;
    private Genre genre;
    @Positive(message = "Duration must be positive")
    private int durationMinutes;
    private AgeRating ageRating;

}
