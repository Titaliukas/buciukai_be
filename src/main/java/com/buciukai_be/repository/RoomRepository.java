package com.buciukai_be.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.Room;

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
}

