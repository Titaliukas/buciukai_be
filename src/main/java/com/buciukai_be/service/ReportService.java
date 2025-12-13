package com.buciukai_be.service;

import com.buciukai_be.api.dto.ClientHistoryReportDto;
import com.buciukai_be.api.dto.ClientReservationDto;
import com.buciukai_be.api.dto.DailyOccupancyReportDto;
import com.buciukai_be.api.dto.HotelRevenueReportDto;
import com.buciukai_be.api.dto.MonthlyDataDto;
import com.buciukai_be.api.dto.OccupiedRoomDto;
import com.buciukai_be.api.dto.ReportListDto;
import com.buciukai_be.api.dto.RoomAvailabilityDto;
import com.buciukai_be.api.dto.RoomStatusReportDto;
import com.buciukai_be.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public DailyOccupancyReportDto getDailyOccupancyReport(Integer hotelId, LocalDate reportDate) {
        // Get hotel information
        Map<String, Object> hotelInfo = reportRepository.getHotelInfo(hotelId);
        if (hotelInfo == null) {
            throw new RuntimeException("Hotel not found");
        }

        // Get occupied rooms
        List<OccupiedRoomDto> occupiedRooms = reportRepository.getOccupiedRoomsByHotelAndDate(hotelId, reportDate);

        // Get total rooms for hotel
        int totalRooms = reportRepository.getTotalRoomsByHotel(hotelId);

        return DailyOccupancyReportDto.builder()
                .reportDate(reportDate)
                .hotelId((Integer) hotelInfo.get("id"))
                .hotelName((String) hotelInfo.get("name"))
                .hotelAddress((String) hotelInfo.get("address"))
                .hotelCity((String) hotelInfo.get("city"))
                .totalRooms(totalRooms)
                .occupiedRooms(occupiedRooms.size())
                .occupiedRoomsList(occupiedRooms)
                .build();
    }

    public ClientHistoryReportDto getClientHistory(String email) {
        // Get client information
        Map<String, Object> clientInfo = reportRepository.getClientInfoByEmail(email);
        if (clientInfo == null) {
            throw new RuntimeException("Client not found");
        }

        String clientId = clientInfo.get("clientId").toString();

        // Get all reservations for the client
        List<ClientReservationDto> reservations = reportRepository.getReservationsByClientId(clientId);

        return ClientHistoryReportDto.builder()
                .clientId(clientId)
                .clientName((String) clientInfo.get("clientName"))
                .clientSurname((String) clientInfo.get("clientSurname"))
                .clientEmail((String) clientInfo.get("clientEmail"))
                .clientPhone((String) clientInfo.get("clientPhone"))
                .totalReservations(reservations.size())
                .reservations(reservations)
                .build();
    }

    public RoomStatusReportDto getRoomStatusReport(Integer hotelId, LocalDate checkDate, String statusFilter) {
        // Get hotel information
        Map<String, Object> hotelInfo = reportRepository.getHotelInfo(hotelId);
        if (hotelInfo == null) {
            throw new RuntimeException("Hotel not found");
        }

        // Get rooms based on status filter
        List<RoomAvailabilityDto> rooms;
        if ("reserved".equalsIgnoreCase(statusFilter)) {
            rooms = reportRepository.getReservedRoomsByHotelAndDate(hotelId, checkDate);
        } else if ("free".equalsIgnoreCase(statusFilter)) {
            rooms = reportRepository.getFreeRoomsByHotelAndDate(hotelId, checkDate);
        } else {
            throw new RuntimeException("Invalid status filter. Use 'reserved' or 'free'");
        }

        // Get total rooms for hotel
        int totalRooms = reportRepository.getTotalRoomsByHotel(hotelId);

        return RoomStatusReportDto.builder()
                .checkDate(checkDate)
                .hotelId((Integer) hotelInfo.get("id"))
                .hotelName((String) hotelInfo.get("name"))
                .hotelAddress((String) hotelInfo.get("address"))
                .hotelCity((String) hotelInfo.get("city"))
                .statusFilter(statusFilter)
                .totalRooms(totalRooms)
                .filteredRoomsCount(rooms.size())
                .roomsList(rooms)
                .build();
    }

    public HotelRevenueReportDto getHotelRevenueReport(Integer hotelId, LocalDate startDate, LocalDate endDate) {
        // Get hotel information
        Map<String, Object> hotelInfo = reportRepository.getHotelInfo(hotelId);
        if (hotelInfo == null) {
            throw new RuntimeException("Hotel not found");
        }

        Integer totalRooms = reportRepository.getTotalRoomsByHotel(hotelId);

        if (totalRooms == null || totalRooms == 0) {
            throw new RuntimeException("Hotel has no rooms configured");
        }

        // Get monthly revenue data
        List<Map<String, Object>> revenueData = reportRepository.getMonthlyRevenueByHotel(hotelId, startDate, endDate);

        // Get monthly occupancy data
        List<Map<String, Object>> occupancyData = reportRepository.getMonthlyOccupancyByHotel(hotelId, startDate,
                endDate, totalRooms);

        // Create maps for easy lookup
        Map<String, BigDecimal> revenueMap = new HashMap<>();
        Map<String, Integer> reservationsMap = new HashMap<>();
        Map<String, Double> occupancyMap = new HashMap<>();

        for (Map<String, Object> data : revenueData) {
            // Convert to integers for consistent key format
            Integer year = ((Number) data.get("year")).intValue();
            Integer month = ((Number) data.get("month")).intValue();
            String key = year + "-" + month;

            // Handle revenue
            Object revenueObj = data.get("revenue");
            BigDecimal revenue = (revenueObj != null)
                    ? (BigDecimal) revenueObj
                    : BigDecimal.ZERO;
            revenueMap.put(key, revenue);

            // Handle reservations - try both column name variations
            Object reservationsObj = data.get("totalReservations");
            if (reservationsObj == null) {
                reservationsObj = data.get("totalreservations");
            }

            Integer reservations = (reservationsObj != null)
                    ? ((Number) reservationsObj).intValue()
                    : 0;
            reservationsMap.put(key, reservations);
        }

        for (Map<String, Object> data : occupancyData) {
            // Convert to integers for consistent key format
            Integer year = ((Number) data.get("year")).intValue();
            Integer month = ((Number) data.get("month")).intValue();
            String key = year + "-" + month;

            // Try possible column name variations (MyBatis returns lowercase)
            Object occupancyRateObj = data.get("occupancyRate");
            if (occupancyRateObj == null) {
                occupancyRateObj = data.get("occupancyrate");
            }

            Double occupancyRate = (occupancyRateObj != null)
                    ? ((Number) occupancyRateObj).doubleValue()
                    : 0.0;

            occupancyMap.put(key, occupancyRate);
        }

        // Build monthly data list
        List<MonthlyDataDto> monthlyDataList = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        double totalOccupancy = 0.0;
        int monthCount = 0;

        LocalDate currentDate = startDate.withDayOfMonth(1);
        LocalDate finalDate = endDate.withDayOfMonth(1);

        while (!currentDate.isAfter(finalDate)) {
            String key = currentDate.getYear() + "-" + currentDate.getMonthValue();

            BigDecimal monthRevenue = revenueMap.getOrDefault(key, BigDecimal.ZERO);
            Double occupancyRate = occupancyMap.getOrDefault(key, 0.0);
            Integer reservations = reservationsMap.getOrDefault(key, 0);

            monthlyDataList.add(MonthlyDataDto.builder()
                    .year(currentDate.getYear())
                    .month(currentDate.getMonthValue())
                    .monthName(Month.of(currentDate.getMonthValue()).name())
                    .revenue(monthRevenue)
                    .occupancyRate(occupancyRate)
                    .totalReservations(reservations)
                    .build());

            totalRevenue = totalRevenue.add(monthRevenue);
            totalOccupancy += occupancyRate;
            monthCount++;

            currentDate = currentDate.plusMonths(1);
        }

        double averageOccupancy = monthCount > 0 ? totalOccupancy / monthCount : 0.0;

        return HotelRevenueReportDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .hotelId((Integer) hotelInfo.get("id"))
                .hotelName((String) hotelInfo.get("name"))
                .hotelAddress((String) hotelInfo.get("address"))
                .hotelCity((String) hotelInfo.get("city"))
                .totalRooms(totalRooms)
                .totalRevenue(totalRevenue)
                .averageOccupancy(averageOccupancy)
                .monthlyData(monthlyDataList)
                .build();
    }

    public Integer saveHotelRevenueReport(String reportName, LocalDate startDate,
            LocalDate endDate, String adminId) {
        return reportRepository.saveReport(reportName, startDate, endDate, adminId);
    }

    public List<ReportListDto> getAllReports() {
        return reportRepository.getAllReports();
    }
}