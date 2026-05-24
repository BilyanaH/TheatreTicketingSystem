package bg.uni.fmi.theatre.repository.seeder;

import bg.uni.fmi.theatre.model.Hall;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.HallRepository;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import bg.uni.fmi.theatre.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(3)
@Profile("!test")
public class PerformanceSeeder implements CommandLineRunner {
    private final PerformanceRepository performances;
    private final ShowRepository shows;
    private final HallRepository halls;

    @Override
    public void run(String... args) throws Exception {
        if (performances.count() > 0) return;

        Show hamlet = shows.findAll().stream()
                .filter(s -> s.getTitle().equals("Hamlet"))
                .findFirst().orElseThrow();
        Hall mainStage = halls.findAll().stream()
                .filter(h -> h.getName().equals("Main Stage"))
                .findFirst().orElseThrow();

        performances.save(new Performance(hamlet, mainStage,
                LocalDateTime.of(2026, 6, 15, 19, 0)));
        performances.save(new Performance(hamlet, mainStage,
                LocalDateTime.of(2026, 6, 22, 19, 0)));
    }
}
