package bg.uni.fmi.theatre.ai;

import bg.uni.fmi.theatre.ai.dto.SearchFiltersResponse;
import bg.uni.fmi.theatre.ai.dto.ShowComparisonResponse;
import bg.uni.fmi.theatre.ai.dto.ShowSummaryResponse;
import bg.uni.fmi.theatre.dto.ShowResponse;
import bg.uni.fmi.theatre.service.ShowService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiShowService {
    private final ChatClient chatClient;
    private final ShowService showService;

    public AiShowService(ChatClient.Builder builder, ShowService showService) {
        this.chatClient = builder
                .defaultSystem("You are a helpful theatre assistant. "
                        + "You recommend shows based on user preferences.")
                .build();
        this.showService = showService;
    }

    public ShowSummaryResponse generateSummary(Long showId) {
        ShowResponse show = showService.getShowById(showId);

        return chatClient.prompt()
                .user("Generate a summary for this theatre event:" + System.lineSeparator()
                        + "Title: " + show.getTitle() + System.lineSeparator()
                        + "Genre: " + show.getGenre() + System.lineSeparator()
                        + "Duration: " + show.getDurationMinutes() + " minutes" + System.lineSeparator()
                        + "Age rating: " + show.getAgeRating() + System.lineSeparator()
                        + "Write a 2-3 sentence summary, identify the target audience, "
                        + "and list 2-4 highlights.")
                .call()
                .entity(ShowSummaryResponse.class);
    }

    public SearchFiltersResponse parseSearchQuery(String naturalLanguageQuery) {
        return chatClient.prompt()
                .user("Extract search filters from this query: " + naturalLanguageQuery + System.lineSeparator()
                        + "Available genres: DRAMA, COMEDY, MUSICAL, OPERA, BALLET + System.lineSeparator()"
                        + "Rules:" + System.lineSeparator()
                        + "- titleKeyword: a keyword to match show titles, or null" + System.lineSeparator()
                        + "- genre: one of the available genres, or null if not mentioned" + System.lineSeparator()
                        + "- maxDurationMinutes: max duration if mentioned (e.g. 'short' = 120), or null" + System.lineSeparator())
                .call()
                .entity(SearchFiltersResponse.class);

    }

    public ShowComparisonResponse compareShows(Long id1, Long id2, String occasion) {
        ShowResponse show1 = showService.getShowById(id1);
        ShowResponse show2 = showService.getShowById(id2);

        return chatClient.prompt()
                .user("Compare these two theatre shows for a " + occasion + ":" + System.lineSeparator()
                        + "Show 1: " + show1.getTitle() + " (" + show1.getGenre() + ", "
                        + show1.getDurationMinutes() + " min " + show1.getDescription() + ")" + System.lineSeparator()
                        + "Show 2: " + show2.getTitle() + " (" + show2.getGenre() + ", "
                        + show2.getDurationMinutes() + " min " + show2.getDescription() + ")" + System.lineSeparator()
                        + "Which show is better for this occasion and why?")
                .call()
                .entity(ShowComparisonResponse.class);
    }
}
