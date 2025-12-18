package com.buciukai_be.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.buciukai_be.model.Announcement;

import java.time.LocalDate;
import java.util.List;

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

    /* existing create() stays */

    @Select("""
        SELECT
            id,
            title,
            message,
            visible_until,
            type
        FROM announcement
        WHERE type = 'NEWS'
          AND visible_until >= #{today}
        ORDER BY id DESC
    """)
    List<Announcement> findActiveNews(LocalDate today);
}
