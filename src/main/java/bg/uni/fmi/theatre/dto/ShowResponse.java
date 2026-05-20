package bg.uni.fmi.theatre.dto;

import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import bg.uni.fmi.theatre.model.Show;
import lombok.Getter;

@Getter
public class ShowResponse {
    private Long id;
    private String title;
    private String description;
    private Genre genre;
    private int durationMinutes;
    private AgeRating ageRating;

    public static ShowResponse from(Show show) {
        ShowResponse showResponse = new ShowResponse();
        showResponse.id = show.getId();
        showResponse.title = show.getTitle();
        showResponse.description = show.getDescription();
        showResponse.genre = show.getGenre();
        showResponse.durationMinutes = show.getDurationMinutes();
        showResponse.ageRating = show.getAgeRating();
        return showResponse;
    }
}
