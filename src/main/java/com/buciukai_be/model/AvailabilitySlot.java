package com.buciukai_be.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AvailabilitySlot {
    private Integer id;
    private Integer roomId;
    private LocalDate startDate;
    private LocalDate endDate;
}
