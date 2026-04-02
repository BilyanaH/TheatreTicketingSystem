package bg.uni.fmi.theatre.repository.inmemory;

import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPerformanceRepository implements PerformanceRepository {
    private final Map<Long, Performance> performanceMap = new HashMap<>();
    private final AtomicLong isSequence = new AtomicLong(1);

    @Override
    public Performance save(Performance performance) {
        performanceMap.put(performance.id(), performance);
        return performance;
    }

    @Override
    public Optional<Performance> findById(Long id) {
        return Optional.ofNullable(performanceMap.get(id));
    }

    @Override
    public List<Performance> findAll() {
        return List.copyOf(performanceMap.values());
    }

    @Override
    public List<Performance> findByShowId(Long showId) {
        return performanceMap.values().stream()
                .filter(x -> x.showId().equals(showId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        performanceMap.remove(id);
    }

    public long nextId() {
        return isSequence.getAndIncrement();
    }
}
