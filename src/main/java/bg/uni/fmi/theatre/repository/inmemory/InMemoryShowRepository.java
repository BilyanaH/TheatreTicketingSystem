package bg.uni.fmi.theatre.repository.inmemory;

import bg.uni.fmi.theatre.domain.Show;
import bg.uni.fmi.theatre.repository.ShowRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryShowRepository implements ShowRepository {
    private final Map<Long, Show> showMap = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Show save(Show show) {
        showMap.put(show.id(), show);
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
