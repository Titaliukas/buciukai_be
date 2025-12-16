package com.buciukai_be.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Exclusion {
    private Integer id;
    private Integer availabilitySlotId; // per schema this stores room_id
    private LocalDate startDate;
    private LocalDate endDate;
}
