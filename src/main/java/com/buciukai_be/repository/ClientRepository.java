package com.buciukai_be.repository;

import com.buciukai_be.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Mapper
@Repository
public interface ClientRepository {

    @Insert("""
                        INSERT INTO buciukai.client (user_id, total_reservations)
                        VALUES (#{firebaseUid}, 0)
                        """)
    void createClient(UUID firebaseUid);
}
