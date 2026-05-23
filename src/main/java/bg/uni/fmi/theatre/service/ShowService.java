package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.config.TheatreProperties;
import bg.uni.fmi.theatre.dto.PageResponse;
import bg.uni.fmi.theatre.dto.ShowRequest;
import bg.uni.fmi.theatre.dto.ShowResponse;
import bg.uni.fmi.theatre.exception.NotFoundException;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.ShowRepository;
import bg.uni.fmi.theatre.vo.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ShowService {
    private final ShowRepository showRepository;
    private final int defaultPageSize;
    private final AtomicLong isSequence = new AtomicLong(100);

    public ShowService(ShowRepository showRepository,
                       TheatreProperties properties) {
        this.showRepository = showRepository;
        this.defaultPageSize = properties.getDefaultPageSize();
    }

    public ShowResponse addShow(ShowRequest request) {
        if (request == null) {
            throw new ValidationException("Show must not be null");
        }
        Show show = new Show(isSequence.getAndIncrement(), request.getTitle(), request.getDescription(),
                request.getGenre(), request.getDurationMinutes(), request.getAgeRating());

        log.debug("Adding show: " + show.getTitle());
        Show saved = showRepository.save(show);

        log.info("Show added: [" + saved.getId() + "] " + saved.getTitle());
        return ShowResponse.from(saved);
    }

    public ShowResponse getShowById(Long id) {
        if (id == null) {
            throw new ValidationException("Show id must not be null");
        }
        log.debug("Fetching show by id: " + id);
        return showRepository.findById(id).map(ShowResponse::from).orElseThrow(() -> {
            log.error("Show not found with id: " + id);
            return new NotFoundException("Show", id);
        });
    }

    public PageResponse<ShowResponse> searchShows(String titleQuery, Genre genre, int page, int size) {
        if (page < 0) {
            throw new ValidationException("Page index must be non-negative");
        }
        if (size <= 0) {
            throw new ValidationException("Page size must be positive");
        }
        log.debug("Searching shows with title query: '" + titleQuery + "', genre: " + genre +
                ", page: " + page + ", size: " + size);

        List<ShowResponse> allResults = showRepository.findAll().stream()
                .filter(s -> (titleQuery == null || titleQuery.isBlank()
                        || s.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
                        && (genre == null || genre.equals(s.getGenre())))
                .sorted(Comparator.comparing(Show::getTitle))
                .map(ShowResponse::from)
                .toList();

        long totalElements = allResults.size();
        log.info("Search found " + totalElements + " shows matching criteria");
        int fromIndex = page * size;
        List<ShowResponse> pageContent = fromIndex >= totalElements ? List.of() :
                allResults.subList(fromIndex, (int) Math.min(fromIndex + size, totalElements));
        return new PageResponse<>(pageContent, page, size, totalElements);
    }

    public PageResponse<ShowResponse> searchShows(String titleQuery, Genre genre) {
        return searchShows(titleQuery, genre, 0, defaultPageSize);
    }

    public List<ShowResponse> getAllShows() {
        log.trace("Fetching all shows");
        return showRepository.findAll().stream()
                .sorted(Comparator.comparing(Show::getTitle))
                .map(ShowResponse::from)
                .toList();
    }

    public ShowResponse updateShow(Long id, ShowRequest request) {
        Show existing = showRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Show", id));
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setGenre(request.getGenre());
        existing.setDurationMinutes(request.getDurationMinutes());
        existing.setAgeRating(request.getAgeRating());
        log.info("Show updated: [" + existing.getId() + "] " + existing.getTitle());
        return ShowResponse.from(showRepository.save(existing));
    }

    public  void deleteShow(Long id) {
        showRepository.findById(id).orElseThrow(() -> new NotFoundException("Show", id));
        showRepository.deleteById(id);
        log.info("Show deleted with id: " + id);
    }
}
