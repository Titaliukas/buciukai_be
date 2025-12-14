package com.buciukai_be.api.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Integer id;
    private Integer roomNumber;
    private Double price;
    private Integer floorNumber;
    private Boolean isAvailable;
    private String description;
    private Double sizeM2;
    private String roomType; // optional: name resolved via join
    private String bedType;  // optional: name resolved via join
    private Long hotelId;
}
