package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByEmail(String email);

    public UserEntity findByUserId(Long userId);


}
