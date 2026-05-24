package bg.uni.fmi.theatre.repository.seeder;

import bg.uni.fmi.theatre.model.Hall;
import bg.uni.fmi.theatre.repository.HallRepository;
import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Order(2)
@Component
@Profile("!test")
public class HallSeeder implements CommandLineRunner {
    private final HallRepository halls;

    public HallSeeder(HallRepository halls) {
        this.halls = halls;
    }

    @Override
    public void run(String... args) throws Exception {
        if (halls.count() > 0) return;

        halls.save(new Hall("Main Stage", 500));
        halls.save(new Hall("Studio Theatre", 120));
        halls.save(new Hall("Open Air Amphitheatre", 800));
    }
}
