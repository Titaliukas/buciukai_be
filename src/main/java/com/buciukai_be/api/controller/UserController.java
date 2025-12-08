package com.buciukai_be.api.controller;

import com.buciukai_be.api.dto.UserInfoDto;
import com.buciukai_be.api.dto.UserSignUpDto;
import com.buciukai_be.model.User;
import com.buciukai_be.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            HttpServletRequest request,
            @RequestBody UserSignUpDto dto
    ) {

        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");

        dto.setFirebaseUid(firebaseUser.getUid());
        dto.setEmail(firebaseUser.getEmail());

        User user = userService.createUser(dto);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDto> getUserByFirebaseUid(HttpServletRequest request){
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");
        String firebaseUid = firebaseUser.getUid();

        UserInfoDto user = userService.getUserByFirebaseUid(firebaseUid);
        return ResponseEntity.ok(user);
    }


    @PatchMapping("/profile/edit")
    public ResponseEntity<String> updateUser(HttpServletRequest request, @RequestBody UserInfoDto dto){
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");
        String firebaseUid = firebaseUser.getUid();

        return userService.updateUser(firebaseUid, dto);
    }

    @DeleteMapping("profile/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest request){
        FirebaseToken firebaseUser =
                (FirebaseToken) request.getAttribute("firebaseUser");
        String firebaseUid = firebaseUser.getUid();

        return userService.deleteUser(firebaseUid);
    }
}
