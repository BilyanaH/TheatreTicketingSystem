package bg.uni.fmi.theatre.repository.inmemory;

import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.ShowRepository;
import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated
@Repository
@Profile("dev")
public abstract class DevInMemoryShowRepository implements ShowRepository {
    private final Map<Long, Show> showMap = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @PostConstruct
    public void seed() {
        save(new Show(nextId(), "Hamlet", "Shakespeare's tragedy", Genre.DRAMA, 120, AgeRating.PG_16));
        save(new Show(nextId(), "Chicago", "Broadway musical", Genre.MUSICAL, 135, AgeRating.PG_12));
        save(new Show(nextId(), "A Midsummer Night's Dream", "Comedy classic", Genre.COMEDY, 95, AgeRating.ALL));
        save(new Show(nextId(), "The Phantom of the Opera", "Epic musical", Genre.MUSICAL, 150, AgeRating.PG_12));
        save(new Show(nextId(), "Swan Lake", "Tchaikovsky's ballet", Genre.BALLET, 140, AgeRating.ALL));
    }

    @Override
    public Show save(Show show) {
        showMap.put(show.getId(), show);
        return show;
    }

    @Override
    public Optional<Show> findById(Long id) {
        return Optional.ofNullable(showMap.get(id));
    }

    @Override
    public List<Show> findAll() {
        return List.copyOf(showMap.values());
    }

    @Override
    public void deleteById(Long id) {
        showMap.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return showMap.containsKey(id);
    }

    public long nextId() {
        return idSequence.getAndIncrement();
    }
}
