package com.buciukai_be.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.Announcement;

@Mapper
@Repository
public interface AnnouncementRepository {

    @Insert("""
        INSERT INTO announcement (
            title,
            message,
            visible_until,
            admin_id,
            type
        )
        VALUES (
            #{title},
            #{message},
            #{visibleUntil},
            #{adminId},
            #{type}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Announcement announcement);
}
