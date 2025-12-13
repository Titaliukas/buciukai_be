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
public class OccupiedRoomDto {
    private Integer roomId;
    private Integer roomNumber;
    private String roomType;
    private String bedType;
    private Integer floorNumber;
    private BigDecimal price;
    private String clientName;
    private String clientSurname;
    private String clientEmail;
    private String clientPhone;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer reservationId;
}