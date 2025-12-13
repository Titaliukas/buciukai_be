package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAvailabilityDto {
    private Integer roomId;
    private Integer roomNumber;
    private String roomType;
    private String bedType;
    private Integer floorNumber;
    private BigDecimal price;
    private BigDecimal sizeM2;
    private String description;
    private Boolean isAvailable;
}