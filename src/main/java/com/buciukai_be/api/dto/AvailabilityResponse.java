package com.buciukai_be.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class AvailabilityResponse {
    private Integer roomId;
    private List<AvailabilitySlotDTO> availabilitySlots;
    private List<ExclusionDTO> exclusions;
}
