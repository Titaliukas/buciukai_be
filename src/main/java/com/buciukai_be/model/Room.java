package com.buciukai_be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private UUID id;

    private UUID hotelId;

    private String roomNumber;
    private BigDecimal price;
    private String roomType;
    private int floor;
    private double size;
    private String bedType;
    private String description;
    private boolean isAvailable;
}

