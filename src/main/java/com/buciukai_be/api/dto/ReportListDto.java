package com.buciukai_be.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportListDto {
    private Integer id;
    private String reportName;
    private LocalDate generationDate;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String adminName;
}