package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.admin.InboxMessageDto;
import com.buciukai_be.service.InboxService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/inbox")
public class InboxController {

    private final InboxService inboxService;

    @GetMapping
    public ResponseEntity<List<InboxMessageDto>> list(HttpServletRequest request) {
        FirebaseToken token = (FirebaseToken) request.getAttribute("firebaseUser");
        return ResponseEntity.ok(inboxService.listInbox(token));
    }

    @PatchMapping("/{inboxId}/read")
    public ResponseEntity<Void> markRead(HttpServletRequest request, @PathVariable int inboxId) {
        FirebaseToken token = (FirebaseToken) request.getAttribute("firebaseUser");
        inboxService.markRead(token, inboxId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{inboxId}")
    public ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable int inboxId) {
        FirebaseToken token = (FirebaseToken) request.getAttribute("firebaseUser");
        inboxService.delete(token, inboxId);
        return ResponseEntity.ok().build();
    }
}
