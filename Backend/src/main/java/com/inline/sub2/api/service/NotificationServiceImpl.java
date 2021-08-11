package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.NotificationEntity;
import com.inline.sub2.db.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;


    @Override
    public NotificationEntity registNotification(NotificationEntity notificationEntity) {
        Date now = new Date();
        notificationEntity.setInviteDate(now);
        notificationEntity.setInviteTime(now);
        return notificationRepository.save(notificationEntity);
    }

    @Override
    public List<NotificationEntity> notificationList(Long userId) {
        return notificationRepository.findByReceiveId(userId);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findByNotificationId(notificationId);
        notificationRepository.delete(notificationEntity);

    }
}
