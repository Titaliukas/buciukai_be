package com.buciukai_be.repository;

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
            id,
            hotel_id     AS hotelId,
            room_number  AS roomNumber,
            price,
            floor_number AS floorNumber,
            is_available AS isAvailable,
            description,
            size_m2      AS sizeM2,
            room_type_id AS roomTypeId,
            bed_type_id  AS bedTypeId
        FROM buciukai.room
        WHERE hotel_id = #{hotelId}
        ORDER BY room_number
    """)
    List<Room> findByHotelId(@Param("hotelId") Integer hotelId);

    @Select("""
        SELECT
            id,
            hotel_id     AS hotelId,
            room_number  AS roomNumber,
            price,
            floor_number AS floorNumber,
            is_available AS isAvailable,
            description,
            size_m2      AS sizeM2,
            room_type_id AS roomTypeId,
            bed_type_id  AS bedTypeId
        FROM buciukai.room
        WHERE id = #{id}
    """)
    Optional<Room> findById(@Param("id") Integer id);
}

