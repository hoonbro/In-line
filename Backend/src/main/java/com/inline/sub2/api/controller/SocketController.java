package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.ChatDto;
import com.inline.sub2.api.dto.SocketVO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/pub/{officeId}")
    // /send로 메시지를 반환합니다.
    @SendTo("/sub/{officeId}")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public SocketVO SocketHandler(SocketVO socketVO, @DestinationVariable("officeId") Long officeId) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();

        ChatDto chatDto = ChatDto.builder()
                .officeId(officeId)
                .sender(userName)
                .content(content)
                .build();


        // 생성자로 반환값을 생성
        SocketVO result = new SocketVO(userName, content);
        // 반환
        return result;
    }
}