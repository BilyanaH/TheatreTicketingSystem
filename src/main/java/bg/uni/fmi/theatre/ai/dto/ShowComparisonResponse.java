package bg.uni.fmi.theatre.ai.dto;

public record ShowComparisonResponse(
        String show1Title,
        String show2Title,
        String recommendation,
        String reasoning
) {
}
