package com.buciukai_be.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AvailabilitySlotDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
