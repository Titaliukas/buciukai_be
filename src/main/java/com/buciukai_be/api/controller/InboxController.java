package com.buciukai_be.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buciukai_be.api.dto.admin.InboxMessageDto;
import com.buciukai_be.service.InboxService;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/inbox")
public class InboxController {

    private final InboxService inboxService;

    /* =========================
       Get inbox for current user
       ========================= */
    @GetMapping
    public ResponseEntity<List<InboxMessageDto>> list(HttpServletRequest request) {
        FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

        return ResponseEntity.ok(
            inboxService.getInbox(token)
        );
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Integer> unreadCount(HttpServletRequest request) {
    FirebaseToken token = (FirebaseToken) request.getAttribute("firebaseUser");
    return ResponseEntity.ok(inboxService.getUnreadCount(token));
    }


    /* =========================
       Mark message as read
       ========================= */
    @PatchMapping("/{inboxId}/read")
    public ResponseEntity<Void> markRead(
        HttpServletRequest request,
        @PathVariable int inboxId
    ) {
        FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

        inboxService.markRead(token, inboxId);
        return ResponseEntity.noContent().build();
    }

    /* =========================
       Delete inbox message
       ========================= */
    @DeleteMapping("/{inboxId}")
    public ResponseEntity<Void> delete(
        HttpServletRequest request,
        @PathVariable int inboxId
    ) {
        FirebaseToken token =
            (FirebaseToken) request.getAttribute("firebaseUser");

        inboxService.delete(token, inboxId);
        return ResponseEntity.noContent().build();
    }
}
