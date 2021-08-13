package com.rtc.groupcall.api.controller;

import com.rtc.groupcall.api.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class SocketController {

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/pub/{officeId}/{roomId}")
    // /send로 메시지를 반환합니다.
    @SendTo("/sub/{officeId}/{roomId}")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public ChatDto SocketHandler(ChatDto chatDto, @DestinationVariable("officeId") Long officeId,  @DestinationVariable("roomId") Long roomId) {
        chatDto.setOfficeId(officeId);
        chatDto.setRoomId(roomId);
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        chatDto.setSendTime(transFormat.format(now).split(" ")[1]);
        return chatDto;
    }
}
