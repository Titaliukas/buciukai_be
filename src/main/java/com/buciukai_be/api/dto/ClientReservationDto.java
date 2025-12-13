package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientReservationDto {
    private Integer reservationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private Integer roomNumber;
    private String roomType;
    private String bedType;
    private Integer floorNumber;
    private BigDecimal price;
    private String hotelName;
    private String hotelCity;
    private String hotelAddress;
}