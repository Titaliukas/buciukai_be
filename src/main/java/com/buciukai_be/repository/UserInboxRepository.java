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
        INSERT INTO buciukai.user_inbox (user_id, announcement_id, is_read)
        VALUES (#{userId}, #{announcementId}, #{isRead})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createInboxRow(UserInbox row);

    @Select("""
        SELECT
            ui.id AS inboxId,
            a.id AS announcementId,
            a.title,
            a.message,
            a.visible_until AS visibleUntil,
            ui.is_read AS isRead,
            ui.received_at AS receivedAt,
            a.admin_id AS adminId
        FROM buciukai.user_inbox ui
        JOIN buciukai.announcement a ON a.id = ui.announcement_id
        WHERE ui.user_id = #{userId}
          AND a.visible_until >= CURRENT_DATE
        ORDER BY ui.received_at DESC
    """)
    List<InboxMessageDto> listInbox(UUID userId);

    @Update("""
        UPDATE buciukai.user_inbox
        SET is_read = TRUE
        WHERE id = #{inboxId} AND user_id = #{userId}
    """)
    void markRead(int inboxId, UUID userId);

    @Delete("""
        DELETE FROM buciukai.user_inbox
        WHERE id = #{inboxId} AND user_id = #{userId}
    """)
    void deleteInbox(int inboxId, UUID userId);
}
