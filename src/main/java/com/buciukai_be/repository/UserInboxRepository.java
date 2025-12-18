package com.buciukai_be.repository;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.buciukai_be.api.dto.admin.InboxMessageDto;

@Mapper
@Repository
public interface UserInboxRepository {

    @Insert("""
        INSERT INTO buciukai.user_inbox
        (user_id, announcement_id)
        VALUES
        (#{userId}, #{announcementId})
    """)
    void insert(
        @Param("userId") UUID userId,
        @Param("announcementId") Integer announcementId
    );

    @Select("""
        SELECT
            ui.id            AS inboxId,
            a.title          AS title,
            a.message        AS message,
            ui.is_read       AS isRead,
            ui.received_at   AS receivedAt
        FROM buciukai.user_inbox ui
        JOIN buciukai.announcement a ON a.id = ui.announcement_id
        WHERE ui.user_id = #{userId}
        ORDER BY ui.received_at DESC
    """)
    List<InboxMessageDto> listInbox(UUID userId);

    @Update("""
        UPDATE buciukai.user_inbox
        SET is_read = TRUE
        WHERE id = #{inboxId}
          AND user_id = #{userId}
    """)
    int markRead(
        @Param("inboxId") int inboxId,
        @Param("userId") UUID userId
    );

    @Delete("""
        DELETE FROM buciukai.user_inbox
        WHERE id = #{inboxId}
          AND user_id = #{userId}
    """)
    int deleteInbox(
        @Param("inboxId") int inboxId,
        @Param("userId") UUID userId
    );
    @Select("""
    SELECT COUNT(*)
    FROM user_inbox
    WHERE user_id = #{userId}
      AND is_read = FALSE
""")
int countUnread(UUID userId);

}


