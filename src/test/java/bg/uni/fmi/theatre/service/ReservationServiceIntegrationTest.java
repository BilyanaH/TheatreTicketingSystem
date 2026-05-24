package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.dto.ReservationRequest;
import bg.uni.fmi.theatre.dto.ReservationResponse;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.model.Hall;
import bg.uni.fmi.theatre.model.Performance;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.HallRepository;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import bg.uni.fmi.theatre.repository.ReservationRepository;
import bg.uni.fmi.theatre.repository.ShowRepository;
import bg.uni.fmi.theatre.vo.AgeRating;
import bg.uni.fmi.theatre.vo.Genre;
import bg.uni.fmi.theatre.vo.ReservationStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private HallRepository hallRepository;

    private Performance testPerformance;

    @BeforeEach
    void setUp() {
        Show show = showRepository.save(
                new Show("Test Show", "desc", Genre.COMEDY, 90, AgeRating.ALL));
        Hall hall = hallRepository.save(new Hall("Test Hall", 100));
        testPerformance = performanceRepository.save(
                new Performance(show, hall, LocalDateTime.of(2026, 9, 1, 19, 0)));
    }

    @Test
    void bookSeat_succeeds_forAvailableSeat() {
        ReservationRequest req = new ReservationRequest();
        req.setPerformanceId(testPerformance.getId());
        req.setSeatLabel("A1");
        req.setCustomerName("Alice");

        ReservationResponse response = reservationService.bookSeat(req);

        assertThat(response.getSeatLabel()).isEqualTo("A1");
        assertThat(response.getCustomerName()).isEqualTo("Alice");
        assertThat(response.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    void bookSeat_fails_whenSeatAlreadyBooked() {
        ReservationRequest req = new ReservationRequest();
        req.setPerformanceId(testPerformance.getId());
        req.setSeatLabel("B2");
        req.setCustomerName("Alice");

        reservationService.bookSeat(req);

        req.setCustomerName("Bob");

        assertThatThrownBy(() -> reservationService.bookSeat(req))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already booked");
    }

    @Test
    void cancelReservation_setsStatusToCancelled() {
        ReservationRequest req = new ReservationRequest();
        req.setPerformanceId(testPerformance.getId());
        req.setSeatLabel("C3");
        req.setCustomerName("Charlie");

        ReservationResponse booked = reservationService.bookSeat(req);
        reservationService.cancelReservation(booked.getId());

        assertThat(reservationService.findByPerformance(testPerformance.getId()))
                .filteredOn(r -> r.getId().equals(booked.getId()))
                .extracting(ReservationResponse::getStatus)
                .containsExactly(ReservationStatus.CANCELLED);
    }
}