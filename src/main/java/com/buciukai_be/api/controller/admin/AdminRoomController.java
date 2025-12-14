package com.buciukai_be.api.controller.admin;

import com.buciukai_be.model.Room;
import com.buciukai_be.service.AdminRoomService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/rooms")
public class AdminRoomController {

    private final AdminRoomService adminRoomService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Room> createRoom(
            HttpServletRequest request,
            @ModelAttribute Room room,
            @RequestPart(required = false) List<MultipartFile> pictures
    ) {
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");

        Room created =
                adminRoomService.createRoom(firebaseUser, room);

        // pictures → later (filesystem / cloud)

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
