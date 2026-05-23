package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.dto.PerformanceResponse;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Setter
@Service
@Slf4j
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final ShowService showService;

    public PerformanceResponse addPerformance(Performance performance) {
        if (performance == null) {
            throw new ValidationException("Performance must not be null");
        }
        showService.getShowById(performance.getShowId());
        log.debug("Adding performance for show: " + performance.getShowId());
        Performance saved = performanceRepository.save(performance);
        log.info("Performance added: id=" + saved.getId());
        return PerformanceResponse.from(saved);
    }

    public List<PerformanceResponse> findPerformancesByShow(Long showId) {
        if (showId == null) {
            throw new ValidationException("showId must not be null");
        }
        showService.getShowById(showId);
        log.debug("Finding performances for show: " + showId);
        List<Performance> performances = performanceRepository.findByShowId(showId);
        log.info("Found " + performances.size() + " performances for show: " + showId);
        return performances.stream().map(PerformanceResponse::from).toList();
    }

    public List<PerformanceResponse> getAllPerformances() {
        log.debug("Fetching all performances");
        List<Performance> performances = performanceRepository.findAll();
        log.info("Found " + performances.size() + " total performances");
        return performances.stream().map(PerformanceResponse::from).toList();
    }
}
