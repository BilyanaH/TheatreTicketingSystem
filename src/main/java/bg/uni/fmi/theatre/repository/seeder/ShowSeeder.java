package bg.uni.fmi.theatre.repository.seeder;

import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.ShowRepository;
import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
@Profile("!test")
public class ShowSeeder implements CommandLineRunner {
    private final ShowRepository shows;

    @Override
    public void run(String... args) throws Exception {
        if (shows.count() > 0) {
            return;
        }
        shows.save(new Show(null, "Hamlet", "Shakespeare's tragedy", Genre.DRAMA, 120, AgeRating.PG_16));
        shows.save(new Show(null, "Chicago", "Broadway musical", Genre.MUSICAL, 135, AgeRating.PG_12));
        shows.save(new Show(null, "A Midsummer Night's Dream", "Comedy classic", Genre.COMEDY, 95, AgeRating.ALL));
        shows.save(new Show(null, "The Phantom of the Opera", "Epic musical", Genre.MUSICAL, 150, AgeRating.PG_12));
        shows.save(new Show(null, "Swan Lake", "Tchaikovsky's ballet", Genre.BALLET, 140, AgeRating.ALL));
    }
}
