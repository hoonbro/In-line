//package com.inline.sub2.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocketMessageBroker //websocket 활성화
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final com.hoon.chat.config.handler.StompHandler stompHandler;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // "/sub" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
//        //  메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 한다.
//        registry.enableSimpleBroker("/sub");
//        registry.setApplicationDestinationPrefixes("/pub");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureClientInboundChannel (ChannelRegistration registration){
//        registration.interceptors(stompHandler);
//    }
//}