package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    public List<NotificationEntity> findByReceiveId(Long userId);
    public NotificationEntity findByNotificationId(Long notificationId);
}
