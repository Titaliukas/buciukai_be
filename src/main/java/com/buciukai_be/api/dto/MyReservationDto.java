package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReservationDto {
    private Integer id;
    private String hotelName;
    private String roomName;
    private String checkIn;
    private String checkOut;
    private Double priceTotal;
    private String address;
    private String image;
    private String status;
}
