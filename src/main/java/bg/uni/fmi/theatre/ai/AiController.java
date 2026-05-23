package bg.uni.fmi.theatre.ai;

import bg.uni.fmi.theatre.ai.dto.SearchFiltersResponse;
import bg.uni.fmi.theatre.ai.dto.ShowComparisonResponse;
import bg.uni.fmi.theatre.ai.dto.ShowSummaryResponse;
import bg.uni.fmi.theatre.dto.PageResponse;
import bg.uni.fmi.theatre.dto.ShowResponse;
import bg.uni.fmi.theatre.service.ShowService;
import bg.uni.fmi.theatre.vo.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
    private final AiShowService aiShowService;
    private final ShowService showService;

    @GetMapping("shows/{id}/summary")
    public ShowSummaryResponse showSummary(@PathVariable Long id) {
        return aiShowService.generateSummary(id);
    }

    @GetMapping("/search")
    public PageResponse<ShowResponse> parseSearchQuery(@RequestParam String naturalLanguageQuery) {
        SearchFiltersResponse searchFiltersResponse = aiShowService.parseSearchQuery(naturalLanguageQuery);
        Genre genre = null;

        if (searchFiltersResponse.genre() != null) {
            try {
                genre = Genre.valueOf(searchFiltersResponse.genre());
            } catch (IllegalArgumentException ignored) {
            }
        }
        return showService.searchShows(searchFiltersResponse.titleKeyword(), genre, 0, 10);
    }

    @GetMapping("/compare")
    public ShowComparisonResponse compareShows(
            @RequestParam Long id1,
            @RequestParam Long id2,
            @RequestParam(defaultValue = "every occasion") String occasion) {
        return aiShowService.compareShows(id1, id2, occasion);
    }

}
