package com.buciukai_be.service;

import com.buciukai_be.api.dto.AvailabilityResponse;
import com.buciukai_be.api.dto.AvailabilitySlotDTO;
import com.buciukai_be.api.dto.AvailabilityUpsertRequest;
import com.buciukai_be.api.dto.ExclusionDTO;
import com.buciukai_be.model.AvailabilitySlot;
import com.buciukai_be.model.Exclusion;
import com.buciukai_be.repository.AvailabilitySlotRepository;
import com.buciukai_be.repository.ExclusionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilitySlotRepository availabilitySlotRepository;
    private final ExclusionRepository exclusionRepository;

    public AvailabilityResponse getAvailability(Integer roomId) {
        List<AvailabilitySlot> slots = availabilitySlotRepository.findByRoomId(roomId);
        List<Exclusion> exclusions = exclusionRepository.findByRoomId(roomId);

        AvailabilityResponse resp = new AvailabilityResponse();
        resp.setRoomId(roomId);
        resp.setAvailabilitySlots(
                slots.stream().map(s -> {
                    AvailabilitySlotDTO dto = new AvailabilitySlotDTO();
                    dto.setStartDate(s.getStartDate());
                    dto.setEndDate(s.getEndDate());
                    return dto;
                }).collect(Collectors.toList()));
        resp.setExclusions(
                exclusions.stream().map(e -> {
                    ExclusionDTO dto = new ExclusionDTO();
                    dto.setStartDate(e.getStartDate());
                    dto.setEndDate(e.getEndDate());
                    return dto;
                }).collect(Collectors.toList()));
        return resp;
    }

    @Transactional
    public AvailabilityResponse upsertAvailability(Integer roomId, AvailabilityUpsertRequest request) {
        // Simple strategy: replace existing slots and exclusions for the room with provided lists
        availabilitySlotRepository.deleteByRoomId(roomId);
        exclusionRepository.deleteByRoomId(roomId);

        if (request.getAvailabilitySlots() != null) {
            for (AvailabilitySlotDTO dto : request.getAvailabilitySlots()) {
                AvailabilitySlot slot = new AvailabilitySlot();
                slot.setRoomId(roomId);
                slot.setStartDate(dto.getStartDate());
                slot.setEndDate(dto.getEndDate());
                availabilitySlotRepository.insert(slot);
            }
        }

        if (request.getExclusions() != null) {
            for (ExclusionDTO dto : request.getExclusions()) {
                Exclusion e = new Exclusion();
                e.setAvailabilitySlotId(roomId); // room_id per schema
                e.setStartDate(dto.getStartDate());
                e.setEndDate(dto.getEndDate());
                exclusionRepository.insert(e);
            }
        }

        return getAvailability(roomId);
    }

    @Transactional
    public void deleteAvailabilityByRoomId(Integer roomId) {
        // Remove all slots and exclusions for the room
        availabilitySlotRepository.deleteByRoomId(roomId);
        exclusionRepository.deleteByRoomId(roomId);
    }
}
