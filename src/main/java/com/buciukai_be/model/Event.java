package com.buciukai_be.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private UUID id;
    private UUID hotelId;

    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private String description;
}
