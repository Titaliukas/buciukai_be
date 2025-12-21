package com.buciukai_be.repository;

import com.buciukai_be.api.dto.RoomDto;
import com.buciukai_be.model.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RoomRepository {

    @Insert("""
        INSERT INTO buciukai.room
        (
            hotel_id,
            room_number,
            price,
            floor_number,
            is_available,
            description,
            size_m2,
            room_type_id,
            bed_type_id
        )
        VALUES
        (
            #{hotelId},
            #{roomNumber},
            #{price},
            #{floorNumber},
            #{isAvailable},
            #{description},
            #{sizeM2},
            #{roomTypeId},
            #{bedTypeId}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createRoom(Room room);

    @Select("""
        SELECT
            r.id,
            r.hotel_id     AS hotelId,
            r.room_number  AS roomNumber,
            r.price,
            r.floor_number AS floorNumber,
            r.is_available AS isAvailable,
            r.description,
            r.size_m2      AS sizeM2,
            rt.name        AS roomType,
            bt.name        AS bedType
        FROM buciukai.room r
        JOIN buciukai.room_type rt ON r.room_type_id = rt.id
        JOIN buciukai.bed_type  bt ON r.bed_type_id  = bt.id
        WHERE r.hotel_id = #{hotelId}
        ORDER BY r.room_number
    """)
    List<RoomDto> findByHotelId(@Param("hotelId") Integer hotelId);

    @Select("""
        SELECT
            r.id,
            r.hotel_id     AS hotelId,
            r.room_number  AS roomNumber,
            r.price,
            r.floor_number AS floorNumber,
            r.is_available AS isAvailable,
            r.description,
            r.size_m2      AS sizeM2,
            rt.name        AS roomType,
            bt.name        AS bedType
        FROM buciukai.room r
        JOIN buciukai.room_type rt ON r.room_type_id = rt.id
        JOIN buciukai.bed_type  bt ON r.bed_type_id  = bt.id
        WHERE r.id = #{id}
    """)
    Optional<RoomDto> findById(@Param("id") Integer id);
}

