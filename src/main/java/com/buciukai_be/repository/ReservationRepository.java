package com.buciukai_be.repository;

import com.buciukai_be.model.Reservation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface ReservationRepository {

    @Insert("""
        INSERT INTO buciukai.reservation (check_in, check_out, status, client_id, room_id)
        VALUES (#{checkIn}, #{checkOut}, #{status}, #{clientId}::uuid, #{roomId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(Reservation reservation);

    @Update("""
        UPDATE buciukai.reservation SET status = 2 WHERE id = #{id}
    """)
    void cancelById(@Param("id") Integer id);

    @Select("""
        SELECT id, check_in AS checkIn, check_out AS checkOut, status, client_id AS clientId, room_id AS roomId
        FROM buciukai.reservation
        WHERE id = #{id}
    """)
    Reservation findById(@Param("id") Integer id);

    @Select("""
        SELECT id, check_in AS checkIn, check_out AS checkOut, status, client_id AS clientId, room_id AS roomId
        FROM buciukai.reservation
        WHERE client_id = #{clientId}::uuid
        ORDER BY check_in DESC
    """)
    List<Reservation> findByClientId(@Param("clientId") UUID clientId);

        @Select("""
                 SELECT r.id,
                     r.check_in AS checkIn,
                     r.check_out AS checkOut,
                     r.status,
                     rm.price AS roomPrice,
                     ht.name AS hotelName,
                     CONCAT('Room ', rm.room_number) AS roomName,
                     ht.address AS hotelAddress
            FROM buciukai.reservation r
            JOIN buciukai.room rm ON r.room_id = rm.id
            JOIN buciukai.hotel ht ON rm.hotel_id = ht.id
            LEFT JOIN buciukai.room_type rt ON rm.room_type_id = rt.id
            WHERE r.client_id = #{clientId}::uuid
            ORDER BY r.check_in DESC
        """)
        List<ReservationSummaryRow> findClientReservationSummaries(@Param("clientId") UUID clientId);

        class ReservationSummaryRow {
            public Integer id;
            public java.sql.Date checkIn;
            public java.sql.Date checkOut;
            public Integer status;
            public Double roomPrice;
            public String hotelName;
            public String roomName;
            public String hotelAddress;
        }

    @Select("""
        SELECT id, check_in AS checkIn, check_out AS checkOut, status, client_id AS clientId, room_id AS roomId
        FROM buciukai.reservation
        WHERE room_id = #{roomId}
          AND status IN (1,3)
          AND (#{checkIn} < check_out AND #{checkOut} > check_in)
    """)
    List<Reservation> findOverlaps(@Param("roomId") Integer roomId,
                                   @Param("checkIn") LocalDate checkIn,
                                   @Param("checkOut") LocalDate checkOut);
}
