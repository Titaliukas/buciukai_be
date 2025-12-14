package com.buciukai_be.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.buciukai_be.model.Room;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.RoomRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminRoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Room createRoom(FirebaseToken token, Room room) {

        User admin = userRepository.getUserByFirebaseUid(token.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (admin.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        room.setIsAvailable(true);
        roomRepository.createRoom(room);
        return room;
    }
}
