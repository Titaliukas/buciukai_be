package com.buciukai_be.repository;

import com.buciukai_be.model.BedType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BedTypeRepository {

    @Select("""
        SELECT id, name
        FROM buciukai.bed_type
        ORDER BY id
    """)
    List<BedType> findAll();
}