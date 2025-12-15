package com.buciukai_be.repository;

import com.buciukai_be.model.Event;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
}
