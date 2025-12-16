package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.AvailabilityResponse;
import com.buciukai_be.api.dto.AvailabilityUpsertRequest;
import com.buciukai_be.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/availability")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<AvailabilityResponse> getAvailability(@PathVariable Integer roomId) {
        return ResponseEntity.ok(availabilityService.getAvailability(roomId));
    }

    @PostMapping
    public ResponseEntity<AvailabilityResponse> upsertAvailability(@PathVariable Integer roomId,
                                                                   @RequestBody AvailabilityUpsertRequest request) {
        return ResponseEntity.ok(availabilityService.upsertAvailability(roomId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAvailability(@PathVariable Integer roomId) {
        availabilityService.deleteAvailabilityByRoomId(roomId);
        return ResponseEntity.noContent().build();
    }
}
