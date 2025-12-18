package com.buciukai_be.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.buciukai_be.api.dto.admin.InboxMessageDto;
import com.buciukai_be.model.User;
import com.buciukai_be.repository.UserInboxRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InboxService {

    private final UserInboxRepository userInboxRepository;
    private final UserRepository userRepository;

    private UUID getCurrentUserId(FirebaseToken token) {
        User u = userRepository.getUserByFirebaseUid(token.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return u.getId();
    }

    public List<InboxMessageDto> listInbox(FirebaseToken token) {
        UUID userId = getCurrentUserId(token);
        return userInboxRepository.listInbox(userId);
    }

    public void markRead(FirebaseToken token, int inboxId) {
        UUID userId = getCurrentUserId(token);
        userInboxRepository.markRead(inboxId, userId);
    }

    public void delete(FirebaseToken token, int inboxId) {
        UUID userId = getCurrentUserId(token);
        userInboxRepository.deleteInbox(inboxId, userId);
    }

    public List<InboxMessageDto> getInbox(FirebaseToken token) {
    UUID userId = getCurrentUserId(token);
    return userInboxRepository.listInbox(userId);
}    
    public int getUnreadCount(FirebaseToken token) {
    UUID userId = getCurrentUserId(token);
    return userInboxRepository.countUnread(userId);
}


}
