package com.buciukai_be.service;

import com.buciukai_be.api.dto.CreateReservationRequest;
import com.buciukai_be.api.dto.MyReservationDto;
import com.buciukai_be.model.Reservation;
import com.buciukai_be.model.Exclusion;
import com.buciukai_be.repository.ReservationRepository;
import com.buciukai_be.repository.ExclusionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ExclusionRepository exclusionRepository;

    @Transactional
    public Reservation createReservation(UUID clientId, CreateReservationRequest req) {
        // basic overlap check
        if (!reservationRepository.findOverlaps(req.getRoomId(), req.getCheckIn(), req.getCheckOut()).isEmpty()) {
            throw new RuntimeException("Room already reserved for selected dates");
        }
        Reservation r = new Reservation();
        r.setClientId(clientId);
        r.setRoomId(req.getRoomId());
        r.setCheckIn(req.getCheckIn());
        r.setCheckOut(req.getCheckOut());
        r.setStatus(1); // confirmed
        reservationRepository.insert(r);
        // add exclusion to prevent double booking
        if (r.getStatus() != null && r.getStatus() == 1) {
            Exclusion ex = new Exclusion();
            ex.setAvailabilitySlotId(r.getRoomId());
            ex.setStartDate(r.getCheckIn());
            ex.setEndDate(r.getCheckOut());
            exclusionRepository.insert(ex);
        }
        return r;
    }

    @Transactional
    public void cancelReservation(Integer id) {
        Reservation existing = reservationRepository.findById(id);
        if (existing != null) {
            exclusionRepository.deleteByRoomIdAndRange(existing.getRoomId(), existing.getCheckIn(), existing.getCheckOut());
        }
        reservationRepository.cancelById(id);
    }

    public List<Reservation> getClientReservations(UUID clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    public List<MyReservationDto> getClientReservationSummaries(UUID clientId) {
        List<ReservationRepository.ReservationSummaryRow> rows = reservationRepository.findClientReservationSummaries(clientId);
        return rows.stream().map(row -> {
            LocalDate checkIn = row.checkIn.toLocalDate();
            LocalDate checkOut = row.checkOut.toLocalDate();
            long nights = Math.max(1, ChronoUnit.DAYS.between(checkIn, checkOut));
            double priceTotal = (row.roomPrice != null ? row.roomPrice : 0.0) * nights;
                return MyReservationDto.builder()
                    .id(row.id)
                    .hotelName(row.hotelName)
                    .roomName(row.roomName)
                    .checkIn(checkIn.toString())
                    .checkOut(checkOut.toString())
                    .priceTotal(priceTotal)
                    .address(row.hotelAddress)
                    .image(null)
                    .status(mapStatus(row.status))
                    .build();
        }).toList();
    }

    private String mapStatus(Integer statusCode) {
        if (statusCode == null) return "Pending";
        switch (statusCode) {
            case 1:
                return "Confirmed";
            case 2:
                return "Cancelled";
            case 3:
                return "Completed";
            default:
                return "Pending";
        }
    }
}
