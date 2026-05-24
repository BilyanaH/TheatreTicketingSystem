package bg.uni.fmi.theatre.web;

import bg.uni.fmi.theatre.dto.ReservationRequest;
import bg.uni.fmi.theatre.dto.ReservationResponse;
import bg.uni.fmi.theatre.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Seat booking operations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse bookSeat(@Valid @RequestBody ReservationRequest req) {
        return reservationService.bookSeat(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @GetMapping
    public List<ReservationResponse> listByPerformance(@RequestParam Long performanceId) {
        return reservationService.findByPerformance(performanceId);
    }
}
