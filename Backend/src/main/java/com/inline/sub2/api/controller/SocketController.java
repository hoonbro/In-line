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

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class SocketController {

    @Autowired
    ChatService chatService;

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/pub/{officeId}")
    // /send로 메시지를 반환합니다.
    @SendTo("/sub/{officeId}")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public ChatDto SocketHandler(ChatDto chatDto, @DestinationVariable("officeId") Long officeId) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        chatDto.setOfficeId(officeId);
        ChatEntity chatEntity = chatService.insertMessage(chatDto);
        System.out.println("내용 확인 : "+ chatEntity.getSendTime());
        chatDto.setSendDate(chatEntity.getSendDate());

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        chatDto.setSendTime(transFormat.format(chatEntity.getSendTime()).split(" ")[1]);
        System.out.println(chatDto.getSendTime());
        return chatDto;
    }
}