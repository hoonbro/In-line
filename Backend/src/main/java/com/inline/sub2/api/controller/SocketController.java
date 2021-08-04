package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class SocketController {

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")

    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줌
    public SocketVO SocketHandler(SocketVO socketVO) {
        System.out.println("소켓 연결됨?");
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();
        // 생성자로 반환값을 생성
        SocketVO result = new SocketVO(userName, content);
        // 반환
        return result;
    }
}