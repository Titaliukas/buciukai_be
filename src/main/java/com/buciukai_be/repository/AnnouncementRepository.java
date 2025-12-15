package com.buciukai_be.repository;

import com.buciukai_be.model.Announcement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface AnnouncementRepository {

    @Insert("""
        INSERT INTO buciukai.announcement
        (title, message, visible_until, type, admin_id)
        VALUES
        (#{title}, #{message}, #{visibleUntil}, #{type}, #{adminId})
        RETURNING id
    """)
    Integer create(Announcement announcement);
}
