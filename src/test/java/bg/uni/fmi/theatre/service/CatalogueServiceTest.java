package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.domain.AgeRating;
import bg.uni.fmi.theatre.domain.Genre;
import bg.uni.fmi.theatre.domain.Performance;
import bg.uni.fmi.theatre.domain.Show;
import bg.uni.fmi.theatre.repository.inmemory.InMemoryPerformanceRepository;
import bg.uni.fmi.theatre.repository.inmemory.InMemoryShowRepository;
import bg.uni.fmi.theatre.service.CatalogueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogueServiceTest {

    private InMemoryShowRepository showRepository;
    private InMemoryPerformanceRepository performanceRepository;
    private CatalogueService catalogueService;

    @BeforeEach
    void setUp() {
        showRepository = new InMemoryShowRepository();
        performanceRepository = new InMemoryPerformanceRepository();
        catalogueService = new CatalogueService(showRepository, performanceRepository, 5);
    }

    void addShowsToShowRepository() {
        catalogueService.addShow(new Show(showRepository.nextId(), "Othello", "Drama", Genre.DRAMA, 110, AgeRating.PG_16));
        catalogueService.addShow(new Show(showRepository.nextId(), "Hamlet", "Drama", Genre.DRAMA, 120, AgeRating.PG_16));
        catalogueService.addShow(new Show(showRepository.nextId(), "A Midsummer Night's Dream", "Comedy", Genre.COMEDY, 90, AgeRating.ALL));
    }

    @Test
    void addShowValidTest() {
        Show show = new Show(showRepository.nextId(), "Hamlet", "Classic drama", Genre.DRAMA, 120, AgeRating.PG_16);
        catalogueService.addShow(show);
        Show found = catalogueService.getShowById(show.id());
        assertEquals("Hamlet", found.title());
    }

    @Test
    void addShowNullTest() {
        assertThrows(IllegalArgumentException.class, () -> catalogueService.addShow(null));
    }

    @Test
    void searchShowsByTitleOnly() {
        addShowsToShowRepository();
        List<Show> results = catalogueService.searchShows("ham", null);
        assertEquals(1, results.size());
        assertEquals("Hamlet", results.getFirst().title());
    }

    @Test
    void searchShowsByGenreOnly() {
        addShowsToShowRepository();
        List<Show> results = catalogueService.searchShows("", Genre.DRAMA);
        assertEquals(2, results.size());
        assertEquals("Hamlet", results.get(0).title());
        assertEquals("Othello", results.get(1).title());
    }

    @Test
    void searchShowsByEmptyQueryReturnsAllShows() {
        addShowsToShowRepository();
        List<Show> results = catalogueService.searchShows("", null);
        assertEquals(3, results.size());
        assertEquals("A Midsummer Night's Dream", results.get(0).title());
        assertEquals("Hamlet", results.get(1).title());
        assertEquals("Othello", results.get(2).title());
    }

    @Test
    void searchShowsPageOutOfBoundsReturnsEmptyList() {
        addShowsToShowRepository();
        List<Show> results = catalogueService.searchShows(null, null, 1, 10);
        assertTrue(results.isEmpty());
    }

    @Test
    void searchShowsNegativePageThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> catalogueService.searchShows(null, null, -1, 10));
    }

    @Test
    void searchShowsZeroSizeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> catalogueService.searchShows(null, null, 0, 0));
    }

    @Test
    void searchShowsPaginationReturnsCorrectPage() {
        for (int i = 1; i <= 8; i++) {
            catalogueService.addShow(new Show(showRepository.nextId(), "Show " + i, "Description", Genre.DRAMA, 90, AgeRating.ALL));
        }
        List<Show> page0 = catalogueService.searchShows(null, null, 0, 3);
        List<Show> page1 = catalogueService.searchShows(null, null, 1, 3);
        List<Show> page2 = catalogueService.searchShows(null, null, 2, 3);
        assertEquals(3, page0.size());
        assertEquals(3, page1.size());
        assertEquals(2, page2.size());
    }

    @Test
    void addPerformanceUnknownShowThrowsException() {
        Performance performance = new Performance(1L, 3L, 1L, LocalDateTime.now().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> catalogueService.addPerformance(performance));
    }

    @Test
    void findPerformancesByShowValidShowReturnsPerformances() {
        Show show = new Show(showRepository.nextId(), "Hamlet", "Drama", Genre.DRAMA, 120, AgeRating.PG_16);
        catalogueService.addShow(show);

        catalogueService.addPerformance(new Performance(performanceRepository.nextId(), show.id(), 1L, LocalDateTime.now().plusDays(1)));
        catalogueService.addPerformance(new Performance(performanceRepository.nextId(), show.id(), 1L, LocalDateTime.now().plusDays(2)));

        List<Performance> performances = catalogueService.findPerformancesByShow(show.id());
        assertEquals(2, performances.size());
    }

    @Test
    void findPerformancesByNonExistingShowThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> catalogueService.findPerformancesByShow(1L));
        }
}