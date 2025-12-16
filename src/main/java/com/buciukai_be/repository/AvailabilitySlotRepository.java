package com.buciukai_be.repository;

import com.buciukai_be.model.AvailabilitySlot;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AvailabilitySlotRepository {

    @Select("""
        SELECT id, start_date AS startDate, end_date AS endDate, room_id AS roomId
        FROM buciukai.availability_slot
        WHERE room_id = #{roomId}
        ORDER BY start_date
    """)
    List<AvailabilitySlot> findByRoomId(@Param("roomId") Integer roomId);

    @Insert("""
        INSERT INTO buciukai.availability_slot (start_date, end_date, room_id)
        VALUES (#{startDate}, #{endDate}, #{roomId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(AvailabilitySlot slot);

    @Delete("""
        DELETE FROM buciukai.availability_slot WHERE room_id = #{roomId}
    """)
    void deleteByRoomId(@Param("roomId") Integer roomId);
}
