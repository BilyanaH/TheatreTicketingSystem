package bg.uni.fmi.theatre.repository;

import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByShow(Show show);

    List<Performance> findByShowId(Long showId);
}
