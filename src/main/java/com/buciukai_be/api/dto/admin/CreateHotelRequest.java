package com.buciukai_be.api.dto.admin;

import lombok.Data;

@Data
public class CreateHotelRequest {
    private String name;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private int starRating;
    private String description;
    private int totalRooms;
}
