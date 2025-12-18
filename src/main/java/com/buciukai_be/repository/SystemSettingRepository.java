package com.buciukai_be.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.SystemSetting;

import java.util.List;

@Mapper
@Repository
public interface SystemSettingRepository {

    @Select("""
        SELECT id, name, is_active, description
        FROM buciukai.system_setting
        WHERE name = #{name}
    """)
    SystemSetting findByName(String name);

    @Select("""
        SELECT id, name, is_active, description
        FROM buciukai.system_setting
        ORDER BY name
    """)
    List<SystemSetting> findAll();

    @Update("""
        UPDATE buciukai.system_setting
        SET is_active = #{isActive}
        WHERE name = #{name}
    """)
    void updateActive(String name, boolean isActive);
}
