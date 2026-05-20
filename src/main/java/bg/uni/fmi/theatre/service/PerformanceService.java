package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.dto.PerformanceResponse;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.logger.AppLogger;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Setter
@Service
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final ShowService showService;
    private final AppLogger logger;

    public PerformanceResponse addPerformance(Performance performance) {
        if (performance == null) {
            throw new ValidationException("Performance must not be null");
        }
        showService.getShowById(performance.getShowId());
        logger.debug("Adding performance for show: " + performance.getShowId());
        Performance saved = performanceRepository.save(performance);
        logger.info("Performance added: id=" + saved.getId());
        return PerformanceResponse.from(saved);
    }

    public List<PerformanceResponse> findPerformancesByShow(Long showId) {
        if (showId == null) {
            throw new ValidationException("showId must not be null");
        }
        showService.getShowById(showId);
        logger.debug("Finding performances for show: " + showId);
        List<Performance> performances = performanceRepository.findByShowId(showId);
        logger.info("Found " + performances.size() + " performances for show: " + showId);
        return performances.stream().map(PerformanceResponse::from).toList();
    }

    public List<PerformanceResponse> getAllPerformances() {
        logger.debug("Fetching all performances");
        List<Performance> performances = performanceRepository.findAll();
        logger.info("Found " + performances.size() + " total performances");
        return performances.stream().map(PerformanceResponse::from).toList();
    }
}
