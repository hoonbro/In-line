package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.ChatDto;
import com.inline.sub2.api.dto.SocketVO;
import com.inline.sub2.api.service.ChatService;
import com.inline.sub2.db.entity.ChatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @Autowired
    ChatService chatService;


    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/pub/{officeId}")
    // /send로 메시지를 반환합니다.
    @SendTo("/sub/{officeId}")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public ChatEntity SocketHandler(ChatDto chatDto, @DestinationVariable("officeId") Long officeId) {
          chatDto.setOfficeId(officeId);
        ChatEntity chatEntity = chatService.insertMessage(chatDto);
        return chatEntity;
    }
}