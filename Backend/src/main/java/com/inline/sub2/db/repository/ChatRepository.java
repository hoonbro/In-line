package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.ChatEntity;
import com.inline.sub2.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity,String> {
    public List<ChatEntity> findByOfficeIdAndSendDate(Long officeId, Date sendDate);
}
