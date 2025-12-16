package com.buciukai_be.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.Event;

@Mapper
@Repository
public interface EventRepository {

    @Insert("""
        INSERT INTO buciukai.event
        (hotel_id, start_date, end_date, description)
        VALUES
        (#{hotelId}, #{startDate}, #{endDate}, #{description})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createEvent(Event event);
}
