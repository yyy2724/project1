package com.api.shop_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // 메시지 브로커가 지원하는 WebSocket 메시지 처리를 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 서버에서 클라이언트로 메세지를 모낼 때

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // 서버에서 클라이언트로 메세지 보낼 때
        config.enableSimpleBroker("/topic");
        // 클라이언트가 메세지를 서버쪽으로 전송할 때
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        // 소켓 연결
        registry.addEndpoint("/chatEndpoint").withSockJS();
    }


}
