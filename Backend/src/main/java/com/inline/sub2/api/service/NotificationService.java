package com.inline.sub2.api.service;


import com.inline.sub2.db.entity.NotificationEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationService {
    @Transactional(rollbackFor = Exception.class)
    NotificationEntity registNotification(NotificationEntity notificationEntity);

    List<NotificationEntity> notificationList(Long userId);

    void deleteNotification(Long notificationId);
}
