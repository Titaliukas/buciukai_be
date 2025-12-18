package com.buciukai_be.repository;

import com.buciukai_be.model.Event;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EventRepository {

    @Insert("""
        INSERT INTO buciukai.event
        (hotel_id, title, description, start_at, end_at, admin_id)
        VALUES
        (#{hotelId}, #{title}, #{description}, #{startAt}, #{endAt}, #{adminId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Event event);

    @Select("""
        SELECT
            id,
            hotel_id   AS hotelId,
            title,
            description,
            start_at   AS startAt,
            end_at     AS endAt,
            admin_id   AS adminId
        FROM buciukai.event
        WHERE hotel_id = #{hotelId}
          AND end_at >= NOW()
        ORDER BY start_at ASC
    """)
    List<Event> findByHotelId(@Param("hotelId") Integer hotelId);
}
