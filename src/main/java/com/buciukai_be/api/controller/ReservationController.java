package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.CreateReservationRequest;
import com.buciukai_be.model.Reservation;
import com.buciukai_be.api.dto.MyReservationDto;
import com.buciukai_be.service.ReservationService;
import com.buciukai_be.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createReservation(HttpServletRequest request,
                                                  @RequestBody CreateReservationRequest req) {
        FirebaseToken firebaseUser = (FirebaseToken) request.getAttribute("firebaseUser");
        UUID clientId = userService.getUuidByFirebaseUid(firebaseUser.getUid());
        Reservation created = reservationService.createReservation(clientId, req);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<MyReservationDto>> myReservations(HttpServletRequest request) {
        FirebaseToken firebaseUser = (FirebaseToken) request.getAttribute("firebaseUser");
        UUID clientId = userService.getUuidByFirebaseUid(firebaseUser.getUid());
        return ResponseEntity.ok(reservationService.getClientReservationSummaries(clientId));
    }
}
