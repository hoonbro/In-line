//package com.inline.sub2.handler;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class StompHandler implements ChannelInterceptor {
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if(StompCommand.CONNECT == accessor.getCommand()) {
//            System.out.println("연결요청입니다 ㅎㅎㅎㅎ");
//            System.out.println(message);
//        }
//        else if(StompCommand.DISCONNECT == accessor.getCommand()) {
//            System.out.println("연결헤제입니다 ㅎㅎㅎㅎ");
//            System.out.println(message);
//        }
//
//
//        return message;
//    }
//
//}
