//package com.inline.sub2.api.handler;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.messaging.support.ChannelInterceptor;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class StompHandler implements ChannelInterceptor {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    //websocket을 통해 들어온 요청이 처리되기 전 실행된다.
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel){
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        //websocket 연결시 jwt token검증
//        if(StompCommand.CONNECT == accessor.getCommand()){
//            jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
//        }
//        return message;
//    }
//}
