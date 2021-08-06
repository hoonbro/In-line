package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.ChatDto;
import com.inline.sub2.db.entity.ChatEntity;
import com.inline.sub2.db.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;


    @Override
    public ChatEntity insertMessage(ChatDto chatDto) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("여기 ㅉ띡히니?");
        Date now = new Date();
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setOfficeId(chatDto.getOfficeId());
        chatEntity.setUserId(chatDto.getUserId());
        chatEntity.setUserName(chatDto.getUserName());
        chatEntity.setContent(chatDto.getContent());
        chatEntity.setSendDate(now);
        chatEntity.setSendTime(now);
        return chatRepository.save(chatEntity);
    }

    @Override
    public List<ChatEntity> chatList(Long officeId) {
        Date sendDate = new Date();
        return chatRepository.findByOfficeIdAndSendDate(officeId,sendDate);
    }
}
