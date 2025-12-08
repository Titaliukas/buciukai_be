package com.buciukai_be.service;

import com.buciukai_be.api.dto.UserInfoDto;
import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import com.buciukai_be.repository.UserRepository;
import com.buciukai_be.api.dto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserSignUpDto dto) {
        // Check if user already exists
        userRepository.getUserByFirebaseUid(dto.getFirebaseUid())
                .ifPresent(u -> {
                    throw new RuntimeException("User already exists");
                });

        // Map DTO to entity
        User user = User.builder()
                .firebaseUid(dto.getFirebaseUid())
                .username(dto.getUsername())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .Role(UserRole.fromCode(dto.getRole()))
                .build();


        // Save user
        userRepository.createUser(user);

        // Handle STAFF / CLIENT extra tables
//        switch (user.getRole()) {
//            case STAFF -> {
//                Staff staff = new Staff();
//                staff.setUser(user);
//                staff.setPosition("Unknown"); // default
//                staff.setSalary(BigDecimal.ZERO);
//                // save via staff repository
//            }
//            case CLIENT -> {
//                Client client = new Client();
//                client.setUser(user);
//                client.setTotalReservations(0);
//                // save via client repository
//            }
//        }

        return user;
    }

    public UserInfoDto getUserByFirebaseUid(String firebaseUid) {
        Optional<User> user = userRepository.getUserByFirebaseUid(firebaseUid);

        if (user.isEmpty()) {
            throw new RuntimeException("User does not exists");
        }

        return UserInfoDto.builder()
                .username(user.get().getUsername())
                .name(user.get().getName())
                .surname(user.get().getSurname())
                .email(user.get().getEmail())
                .birthdate(user.get().getBirthdate())
                .city(user.get().getCity())
                .phoneNumber(user.get().getPhoneNumber())
                .postalCode(user.get().getPostalCode())
                .role(user.get().getRole())
                .build();
    }

    public ResponseEntity<String> updateUser(String firebaseUid, UserInfoDto dto) {
        userRepository.updateUser(firebaseUid, dto);
        return ResponseEntity.ok("User updated successfully");
    }

    public  ResponseEntity<String> deleteUser(String firebaseUid) {
        userRepository.deleteUser(firebaseUid);
        return ResponseEntity.ok("User deleted successfully");
    }
}
