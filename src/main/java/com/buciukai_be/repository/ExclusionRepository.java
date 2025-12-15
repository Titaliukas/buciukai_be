package com.buciukai_be.repository;

import com.buciukai_be.model.Exclusion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExclusionRepository {

    @Select("""
        SELECT id, start_date AS startDate, end_date AS endDate, room_id AS availabilitySlotId
        FROM buciukai.exclusion
        WHERE room_id = #{roomId}
        ORDER BY start_date
    """)
    List<Exclusion> findByRoomId(@Param("roomId") Integer roomId);

    @Insert("""
        INSERT INTO buciukai.exclusion (start_date, end_date, room_id)
        VALUES (#{startDate}, #{endDate}, #{availabilitySlotId})
    """)
    void insert(Exclusion exclusion);

    @Delete("""
        DELETE FROM buciukai.exclusion WHERE room_id = #{roomId}
    """)
    void deleteByRoomId(@Param("roomId") Integer roomId);
}
