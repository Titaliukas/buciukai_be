package com.buciukai_be.repository;

import com.buciukai_be.api.dto.UserInfoDto;
import com.buciukai_be.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

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
            SELECT id, firebase_uid, username, name, surname, email, phone_number, birthdate, city, postal_code, role, created_at
            FROM buciukai.users
            WHERE firebase_uid = #{firebaseUid}
            """)
    Optional<User> getUserByFirebaseUid(String firebaseUid);

    @Update(
            """
            UPDATE buciukai.users
            SET username=#{user.username}, name=#{user.name}, surname=#{user.surname}, email=#{user.email}, phone_number=#{user.phoneNumber}, birthdate=#{user.birthdate}, city=#{user.city}, postal_code=#{user.postalCode}
            WHERE firebase_uid = #{firebaseUid}
            """
    )
    void updateUser(String firebaseUid, UserInfoDto user);

    @Delete(
            """
            DELETE FROM buciukai.users
            WHERE firebase_uid = #{firebaseUid}
            """
    )
    void deleteUser(String firebaseUid);
}