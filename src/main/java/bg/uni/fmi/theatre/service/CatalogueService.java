package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.model.Genre;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import bg.uni.fmi.theatre.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogueService {
    private final ShowRepository showRepository;
    private final PerformanceRepository performanceRepository;
    private final int pageSize;

    public CatalogueService(ShowRepository showRepository, PerformanceRepository performanceRepository,
                            @Value("${catalogue.page-size}")int pageSize) {
        if (showRepository == null) {
            throw new IllegalArgumentException("ShowRepository cannot be null");
        }
        if (performanceRepository == null) {
            throw new IllegalArgumentException("PerformanceRepository cannot be null");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be positive number");
        }
        this.showRepository = showRepository;
        this.performanceRepository = performanceRepository;
        this.pageSize = pageSize;
    }

    public Show addShow(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show cannot be null");
        }
        return showRepository.save(show);
    }

    public Show getShowById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Show id cannot be null");
        }
        Optional<Show> show = showRepository.findById(id);
        if (show.isEmpty()) {
            throw new IllegalArgumentException("Show is not existing");
        }
        return show.get();
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> searchShows(String titleQuery, Genre genre) {
        return searchShows(titleQuery, genre, 0, pageSize);
    }

    public List<Show> searchShows(String titleQuery, Genre genre, int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be nonnegative number");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be positive number");
        }
        List<Show> filteredShows = showRepository.findAll().stream()
                .filter(x -> titleQuery == null || titleQuery.isBlank() ||
                        x.title().toLowerCase().contains(titleQuery.toLowerCase()))
                .filter(x -> genre == null || x.genre().equals(genre))
                .sorted(Comparator.comparing(Show::title))
                .toList();

        int startIndex = page * size;
        if (filteredShows.size() <= startIndex) {
            return List.of();
        }
        int endIndex = Math.min(startIndex + size, filteredShows.size());
        return filteredShows.subList(startIndex, endIndex);
    }

    public Performance addPerformance(Performance performance) {
        if (performance == null) {
            throw new IllegalArgumentException("Performance cannot be null");
        }
        performanceRepository.save(performance);
        if (!showRepository.existsById(performance.showId())) {
            throw new IllegalArgumentException("Show not found: " + performance.showId());
        }
        return performance;
    }

    public List<Performance> findPerformancesByShow(Long showId) {
        if (showId == null) {
            throw new IllegalArgumentException("Show id cannot be null");
        }
        if (!showRepository.existsById(showId)) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        return performanceRepository.findByShowId(showId);
    }
}
