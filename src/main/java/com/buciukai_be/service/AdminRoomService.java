package com.buciukai_be.service;

import com.buciukai_be.model.Room;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.RoomRepository;
import com.buciukai_be.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AdminRoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Room createRoom(FirebaseToken firebaseUser, Room room) {

        User user = userRepository
                .getUserByFirebaseUid(firebaseUser.getUid())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "User not registered in system"
                        )
                );

        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "ADMIN role required"
            );
        }

        room.setAvailable(true);
        roomRepository.createRoom(room);

        return room;
    }
}
