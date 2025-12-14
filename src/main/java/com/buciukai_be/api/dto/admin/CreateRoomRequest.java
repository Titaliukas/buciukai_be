package com.buciukai_be.api.dto.admin;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private Long hotelId;
    private String roomNumber;
    private BigDecimal price;
    private String roomType;
    private int floor;
    private double size;
    private String bedType;
    private String description;
}
