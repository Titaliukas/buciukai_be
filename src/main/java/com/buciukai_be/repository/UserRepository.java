package com.buciukai_be.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.buciukai_be.api.dto.UserInfoDto;
import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.model.User;

@Mapper
@Repository
public interface UserRepository {

        @Insert("""
                        INSERT INTO buciukai.users (firebase_uid, username, name, surname, email, role)
                        VALUES (#{firebaseUid}, #{username}, #{name}, #{surname}, #{email}, #{role})
                        """)
        @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
        void createUser(User user);

        @Select("""
                        SELECT id, firebase_uid, username, name, surname, email, phone_number, birthdate, city, postal_code, role, created_at
                        FROM buciukai.users
                        WHERE firebase_uid = #{firebaseUid}
                        """)
        Optional<User> getUserByFirebaseUid(String firebaseUid);

        @Update("""
                        UPDATE buciukai.users
                        SET username=#{user.username}, name=#{user.name}, surname=#{user.surname}, email=#{user.email}, phone_number=#{user.phoneNumber}, birthdate=#{user.birthdate}, city=#{user.city}, postal_code=#{user.postalCode}
                        WHERE firebase_uid = #{firebaseUid}
                        """)
        void updateUser(String firebaseUid, UserInfoDto user);

        @Delete("""
                        DELETE FROM buciukai.users
                        WHERE firebase_uid = #{firebaseUid}
                        """)
        void deleteUser(String firebaseUid);

        @Update("""
    UPDATE buciukai.users
    SET status_id = 2
    WHERE id = #{userId}
""")
void blockUser(UUID userId);

@Update("""
    UPDATE buciukai.users
    SET status_id = 1
    WHERE id = #{userId}
""")
void unblockUser(UUID userId);

@Update("""
    UPDATE buciukai.users
    SET email = #{email}
    WHERE id = #{userId}
""")
void updateEmail(UUID userId, String email);

@Select("""
    SELECT
        id,
        firebase_uid AS firebaseUid,
        name,
        surname,
        email,
        city,
        birthdate,
        (status_id = 2) AS isBlocked
    FROM buciukai.users
    WHERE role = 1
    ORDER BY created_at DESC
""")
List<AdminUserDto> getAllClients();

@Select("""
    SELECT
        id,
        firebase_uid,
        username,
        name,
        surname,
        email,
        phone_number,
        birthdate,
        city,
        postal_code,
        role,
        status_id,
        created_at
    FROM buciukai.users
    WHERE id = #{userId}
""")
Optional<User> getUserById(UUID userId);
}