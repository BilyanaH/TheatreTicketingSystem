package bg.uni.fmi.theatre.ai.dto;

import java.util.List;

public record ShowSummaryResponse(
        String summary,
        String targetAudience,
        List<String> highlights) {
}
