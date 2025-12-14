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
        (hotel_id, room_number, price, room_type, floor, size, bed_type, description, is_available)
        VALUES
        (#{hotelId}, #{roomNumber}, #{price}, #{roomType}, #{floor}, #{size}, #{bedType}, #{description}, #{isAvailable})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createRoom(Room room);
}
