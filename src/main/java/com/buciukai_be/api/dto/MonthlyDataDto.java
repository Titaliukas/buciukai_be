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
public class MonthlyDataDto {
    private Integer year;
    private Integer month;
    private String monthName;
    private BigDecimal revenue;
    private Double occupancyRate;
    private Integer totalReservations;
}