package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.dto.PerformanceRequest;
import bg.uni.fmi.theatre.dto.PerformanceResponse;
import bg.uni.fmi.theatre.exception.NotFoundException;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.model.Hall;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.HallRepository;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import bg.uni.fmi.theatre.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Service
@Slf4j
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final ShowRepository showRepository;
    private final HallRepository hallRepository;

    public PerformanceResponse addPerformance(PerformanceRequest performanceRequest) {
        Show show = showRepository.findById(performanceRequest.getShowId())
                .orElseThrow(() -> new NotFoundException("Show", performanceRequest.getShowId()));
        Hall hall = hallRepository.findById(performanceRequest.getHallId())
                .orElseThrow(() -> new NotFoundException("Hall", performanceRequest.getHallId()));

        log.debug("Adding performance for show: " + performanceRequest.getShowId());
        Performance saved = performanceRepository.save( new Performance(show, hall, performanceRequest.getStartTime()));
        log.info("Performance added: id=" + saved.getId());
        return PerformanceResponse.from(saved);
    }

    public List<PerformanceResponse> findPerformancesByShow(Long showId) {
        if (showId == null) {
            throw new ValidationException("showId must not be null");
        }
        showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("Show", showId));

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
