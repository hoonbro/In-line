package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.ChatDto;
import com.inline.sub2.db.entity.ChatEntity;

import java.util.Date;
import java.util.List;

public interface ChatService {
    ChatEntity insertMessage(ChatDto chatDto);
    List<ChatEntity> chatList(Long officeId);
}
