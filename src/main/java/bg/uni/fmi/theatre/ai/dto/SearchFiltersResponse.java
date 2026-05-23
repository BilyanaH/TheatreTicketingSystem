package bg.uni.fmi.theatre.ai.dto;

public record SearchFiltersResponse(
        String titleKeyword,
        String genre,
        Integer maxDurationMinutes
) {
}
