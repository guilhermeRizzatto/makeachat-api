package com.rizzatto.chat_preview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private UserHandshakeInterceptor userHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue"); // /queue para mensagens privadas
        config.setApplicationDestinationPrefixes("/makeachat");
        config.setUserDestinationPrefix("/user"); // importante para @SendToUser
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/chat")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(userHandshakeInterceptor)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
