package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRevenueReportDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private Integer totalRooms;
    private BigDecimal totalRevenue;
    private Double averageOccupancy;
    private List<MonthlyDataDto> monthlyData;
}