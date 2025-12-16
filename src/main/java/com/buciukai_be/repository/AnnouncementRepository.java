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
        INSERT INTO buciukai.announcement (title, message, visible_until, admin_id)
        VALUES (#{title}, #{message}, #{visibleUntil}, #{adminId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createAnnouncement(Announcement a);

    @Select("""
        SELECT a.id, a.title, a.message, a.visible_until, a.admin_id
        FROM buciukai.announcement a
        WHERE a.visible_until >= #{today}
          AND NOT EXISTS (
              SELECT 1 FROM buciukai.user_inbox ui
              WHERE ui.announcement_id = a.id
          )
        ORDER BY a.id DESC
    """)
    List<Announcement> listNews(LocalDate today);
}
