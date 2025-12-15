package com.buciukai_be.repository;

import com.buciukai_be.api.dto.admin.InboxMessageDto;
import com.buciukai_be.model.UserInbox;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
        FROM user_inbox ui
        JOIN announcement a ON a.id = ui.announcement_id
        WHERE ui.user_id = #{userId}
        ORDER BY ui.received_at DESC
    """)
    List<InboxMessageDto> listInbox(UUID userId);

    @Update("""
        UPDATE user_inbox
        SET is_read = TRUE
        WHERE id = #{inboxId}
          AND user_id = #{userId}
    """)
    int markRead(@Param("inboxId") int inboxId,
                 @Param("userId") UUID userId);

    @Delete("""
        DELETE FROM user_inbox
        WHERE id = #{inboxId}
          AND user_id = #{userId}
    """)
    int deleteInbox(@Param("inboxId") int inboxId,
                    @Param("userId") UUID userId);
}

