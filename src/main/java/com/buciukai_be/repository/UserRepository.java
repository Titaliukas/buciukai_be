package com.buciukai_be.repository;

import com.buciukai_be.model.User;
import com.buciukai_be.model.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Mapper
@Repository
public interface UserRepository {

    @Insert(
            """
            INSERT INTO buciukai.users (firebase_uid, username, name, surname, email, role)
            VALUES (#{firebaseUid}, #{username}, #{name}, #{surname}, #{email}, #{role})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createUser(User user);

    @Select(
            """
            SELECT id, firebase_uid, username, name, surname, email, phone_number, birthdate, city, role, created_at
            FROM buciukai.users
            WHERE firebase_uid = #{firebaseUid}
            """)
    Optional<User> findByFirebaseUid(String firebaseUid);
}
