package com.inline.sub2.config;

import com.inline.sub2.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker //websocket 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final com.inline.sub2.handler.StompHandler stompHandler;
    @Autowired
    private StompHandler stompHandler;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/sub" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
        //  메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 한다.
//        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub","/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel (ChannelRegistration registration){
        registration.interceptors(stompHandler);
    }
}