package bg.uni.fmi.theatre.dto;

import bg.uni.fmi.theatre.model.Reservation;
import bg.uni.fmi.theatre.vo.ReservationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponse {
    private Long id;
    private Long performanceId;
    private String seatLabel;
    private String customerName;
    private ReservationStatus status;
    private LocalDateTime reservedAt;

    public static ReservationResponse from(Reservation r) {
        ReservationResponse res = new ReservationResponse();
        res.id = r.getId();
        res.performanceId = r.getPerformance().getId();
        res.seatLabel = r.getSeatLabel();
        res.customerName = r.getCustomerName();
        res.status = r.getStatus();
        res.reservedAt = r.getReservedAt();
        return res;
    }
}