package bg.uni.fmi.theatre.repository;

import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ShowRepositoryTest {

    @Autowired
    private ShowRepository showRepository;

    @Test
    void save_and_findById_roundTrip() {
        Show saved = showRepository.save(
                new Show("Hamlet", "tragedy", Genre.DRAMA, 180, AgeRating.PG_16));

        assertThat(saved.getId()).isNotNull();
        assertThat(showRepository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(Show::getTitle)
                .isEqualTo("Hamlet");
    }

    @Test
    void findAll_returnsAllSavedShows() {
        showRepository.save(new Show("Show A", "desc", Genre.DRAMA, 120, AgeRating.PG_16));
        showRepository.save(new Show("Show B", "desc", Genre.BALLET, 100, AgeRating.ALL));

        assertThat(showRepository.findAll())
                .extracting(Show::getTitle)
                .contains("Show A", "Show B");
    }

    @Test
    void deleteById_removesShow() {
        Show saved = showRepository.save(
                new Show("To Delete", "desc", Genre.OPERA, 150, AgeRating.PG_12));

        showRepository.deleteById(saved.getId());

        assertThat(showRepository.findById(saved.getId())).isEmpty();
    }
}