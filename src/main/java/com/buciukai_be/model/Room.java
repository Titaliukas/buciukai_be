package com.buciukai_be.model;

import lombok.Data;

@Data
public class Room {
    private Integer id;

    private Integer hotelId;
    private Integer roomNumber;
    private Double price;
    private Integer floorNumber;
    private Boolean isAvailable;
    private String description;
    private Double sizeM2;

    private Integer roomTypeId;
    private Integer bedTypeId;
}
