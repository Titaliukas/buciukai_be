package com.buciukai_be.repository;

import com.buciukai_be.api.dto.admin.AdminUserDto;
import com.buciukai_be.api.dto.admin.AdminUserRawDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface AdminUserRepository {

    @Select("""
        SELECT 
            id,
            name,
            surname,
            email,
            city,
            birthdate,
            role,
            is_blocked AS isBlocked
        FROM buciukai.users
        WHERE role = 1
        ORDER BY created_at DESC
    """)
    List<AdminUserDto> getAllClients();

    @Update("""
        UPDATE buciukai.users
        SET is_blocked = TRUE
        WHERE id = #{userId}
    """)
    void blockUser(@Param("userId") UUID userId);

    @Update("""
        UPDATE buciukai.users
        SET is_blocked = FALSE
        WHERE id = #{userId}
    """)
    void unblockUser(@Param("userId") UUID userId);

    @Update("""
        UPDATE buciukai.users
        SET email = #{email}
        WHERE id = #{userId}
    """)
    void updateEmail(
        @Param("userId") UUID userId,
        @Param("email") String email
    );

    @Select("""
    SELECT 
        id,
        name,
        surname,
        email,
        city,
        birthdate,
        role,
        is_blocked AS isBlocked
    FROM buciukai.users
    ORDER BY created_at DESC
""")
List<AdminUserRawDto> getAllUsers();

}
