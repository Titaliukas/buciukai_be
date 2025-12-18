package com.buciukai_be.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.buciukai_be.model.RoomType;

@Mapper
public interface RoomTypeRepository {

    @Select("""
        SELECT id, name
        FROM room_type
        ORDER BY id
    """)
    List<RoomType> findAll();
}
