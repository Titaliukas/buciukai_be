package com.buciukai_be.repository;

import com.buciukai_be.api.dto.ClientReservationDto;
import com.buciukai_be.api.dto.OccupiedRoomDto;
import com.buciukai_be.api.dto.RoomAvailabilityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ReportRepository {

        @Select("""
                        SELECT
                            id,
                            name,
                            address,
                            city,
                            total_rooms as totalRooms
                        FROM buciukai.hotel
                        WHERE id = #{hotelId}
                        """)
        Map<String, Object> getHotelInfo(@Param("hotelId") Integer hotelId);

        @Select("""
                        SELECT
                            r.id as roomId,
                            r.room_number as roomNumber,
                            rt.name as roomType,
                            bt.name as bedType,
                            r.floor_number as floorNumber,
                            r.price,
                            u.name as clientName,
                            u.surname as clientSurname,
                            u.email as clientEmail,
                            u.phone_number as clientPhone,
                            res.check_in as checkIn,
                            res.check_out as checkOut,
                            res.id as reservationId
                        FROM buciukai.room r
                        INNER JOIN buciukai.room_type rt ON r.room_type_id = rt.id
                        INNER JOIN buciukai.bed_type bt ON r.bed_type_id = bt.id
                        INNER JOIN buciukai.reservation res ON r.id = res.room_id
                        INNER JOIN buciukai.client c ON res.client_id = c.user_id
                        INNER JOIN buciukai.users u ON c.user_id = u.id
                        WHERE r.hotel_id = #{hotelId}
                        AND res.status IN (1, 3)
                        AND #{reportDate} >= res.check_in
                        AND #{reportDate} < res.check_out
                        ORDER BY r.room_number
                        """)
        List<OccupiedRoomDto> getOccupiedRoomsByHotelAndDate(
                        @Param("hotelId") Integer hotelId,
                        @Param("reportDate") LocalDate reportDate);

        @Select("""
                        SELECT COUNT(*)
                        FROM buciukai.room
                        WHERE hotel_id = #{hotelId}
                        """)
        int getTotalRoomsByHotel(@Param("hotelId") Integer hotelId);

        @Select("""
                        SELECT
                            u.id as "clientId",
                            u.name as "clientName",
                            u.surname as "clientSurname",
                            u.email as "clientEmail",
                            u.phone_number as "clientPhone"
                        FROM buciukai.users u
                        INNER JOIN buciukai.client c ON u.id = c.user_id
                        WHERE u.email = #{email}
                        """)
        Map<String, Object> getClientInfoByEmail(@Param("email") String email);

        @Select("""
                        SELECT
                            res.id as reservationId,
                            res.check_in as checkIn,
                            res.check_out as checkOut,
                            rs.name as status,
                            r.room_number as roomNumber,
                            rt.name as roomType,
                            bt.name as bedType,
                            r.floor_number as floorNumber,
                            r.price,
                            h.name as hotelName,
                            h.city as hotelCity,
                            h.address as hotelAddress
                        FROM buciukai.reservation res
                        INNER JOIN buciukai.reservation_status rs ON res.status = rs.id
                        INNER JOIN buciukai.room r ON res.room_id = r.id
                        INNER JOIN buciukai.room_type rt ON r.room_type_id = rt.id
                        INNER JOIN buciukai.bed_type bt ON r.bed_type_id = bt.id
                        INNER JOIN buciukai.hotel h ON r.hotel_id = h.id
                        WHERE res.client_id = #{clientId}::uuid
                        ORDER BY res.check_in DESC
                        """)
        List<ClientReservationDto> getReservationsByClientId(@Param("clientId") String clientId);

        @Select("""
                        SELECT
                            r.id as roomId,
                            r.room_number as roomNumber,
                            rt.name as roomType,
                            bt.name as bedType,
                            r.floor_number as floorNumber,
                            r.price,
                            r.size_m2 as sizeM2,
                            r.description,
                            r.is_available as isAvailable
                        FROM buciukai.room r
                        INNER JOIN buciukai.room_type rt ON r.room_type_id = rt.id
                        INNER JOIN buciukai.bed_type bt ON r.bed_type_id = bt.id
                        WHERE r.hotel_id = #{hotelId}
                        AND EXISTS (
                            SELECT 1
                            FROM buciukai.reservation res
                            WHERE res.room_id = r.id
                            AND res.status IN (1, 3)
                            AND #{checkDate} >= res.check_in
                            AND #{checkDate} < res.check_out
                        )
                        ORDER BY r.room_number
                        """)
        List<RoomAvailabilityDto> getReservedRoomsByHotelAndDate(
                        @Param("hotelId") Integer hotelId,
                        @Param("checkDate") LocalDate checkDate);

        @Select("""
                        SELECT
                            r.id as roomId,
                            r.room_number as roomNumber,
                            rt.name as roomType,
                            bt.name as bedType,
                            r.floor_number as floorNumber,
                            r.price,
                            r.size_m2 as sizeM2,
                            r.description,
                            r.is_available as isAvailable
                        FROM buciukai.room r
                        INNER JOIN buciukai.room_type rt ON r.room_type_id = rt.id
                        INNER JOIN buciukai.bed_type bt ON r.bed_type_id = bt.id
                        WHERE r.hotel_id = #{hotelId}
                        AND NOT EXISTS (
                            SELECT 1
                            FROM buciukai.reservation res
                            WHERE res.room_id = r.id
                            AND res.status IN (1, 3)
                            AND #{checkDate} >= res.check_in
                            AND #{checkDate} < res.check_out
                        )
                        ORDER BY r.room_number
                        """)
        List<RoomAvailabilityDto> getFreeRoomsByHotelAndDate(
                        @Param("hotelId") Integer hotelId,
                        @Param("checkDate") LocalDate checkDate);

        @Select("""
                        SELECT
                            EXTRACT(YEAR FROM res.check_in) as year,
                            EXTRACT(MONTH FROM res.check_in) as month,
                            SUM(r.price * (res.check_out - res.check_in)) as revenue,
                            COUNT(res.id) as totalReservations
                        FROM buciukai.reservation res
                        INNER JOIN buciukai.room r ON res.room_id = r.id
                        WHERE r.hotel_id = #{hotelId}
                        AND res.status IN (1, 3)
                        AND res.check_in >= #{startDate}
                        AND res.check_in <= #{endDate}
                        GROUP BY EXTRACT(YEAR FROM res.check_in), EXTRACT(MONTH FROM res.check_in)
                        ORDER BY year, month
                        """)
        List<Map<String, Object>> getMonthlyRevenueByHotel(
                        @Param("hotelId") Integer hotelId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Select("""
                        WITH date_room_occupancy AS (
                            SELECT
                                DATE(date_series) as occupancy_date,
                                EXTRACT(YEAR FROM date_series) as year,
                                EXTRACT(MONTH FROM date_series) as month,
                                COUNT(res.id) as occupied_rooms_count
                            FROM generate_series(#{startDate}::date, #{endDate}::date, '1 day'::interval) as date_series
                            LEFT JOIN buciukai.reservation res ON
                                res.room_id IN (SELECT id FROM buciukai.room WHERE hotel_id = #{hotelId})
                                AND res.status IN (1, 3)
                                AND date_series >= res.check_in
                                AND date_series < res.check_out
                            GROUP BY DATE(date_series), EXTRACT(YEAR FROM date_series), EXTRACT(MONTH FROM date_series)
                        )
                        SELECT
                            year,
                            month,
                            COALESCE(
                                (SUM(occupied_rooms_count)::DECIMAL / (COUNT(*) * #{totalRooms})) * 100,
                                0.0
                            ) as occupancyRate
                        FROM date_room_occupancy
                        GROUP BY year, month
                        ORDER BY year, month
                        """)
        List<Map<String, Object>> getMonthlyOccupancyByHotel(
                        @Param("hotelId") Integer hotelId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("totalRooms") Integer totalRooms);
}