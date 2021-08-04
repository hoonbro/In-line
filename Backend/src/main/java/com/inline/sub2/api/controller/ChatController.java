//package com.inline.sub2.api.controller;
//
//import com.inline.sub2.api.dto.ChatAllMessage;
//import com.inline.sub2.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Controller;
//
//@RequiredArgsConstructor
//@Controller
//
//public class ChatController {
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    private final JwtUtil jwtUtil;
//
////    private final ChatRoomRepoImpl chatRoomRepository;
//
//    @MessageMapping("/chat/message")
//    public void message(ChatAllMessage message, @Header("token") String token) {
//        String userName = jwtUtil.getUserNameFromJwt(token);
//        System.out.println("여기는 들어오니?");
//        //로그인 회원 정보로 대화명 설정
//        message.setSender(userName);
//
//        if(ChatAllMessage.MessageType.ENTER.equals(message.getType())){
//            message.setSender(("[알림]"));
//            message.setMessage(userName + "님이 입장하셨습니다.");
//        }
//
//        messagingTemplate.convertAndSend("/sub/chat/office/"+message.getRoomId(),message);
//    }
//}