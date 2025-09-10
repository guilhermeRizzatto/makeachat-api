package com.rizzatto.chat_preview.config;

import com.rizzatto.chat_preview.security.SecurityFilter;
import com.rizzatto.chat_preview.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    JwtService jwtService;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();

            String token = SecurityFilter.recoverToken(httpRequest);
            String username = jwtService.getSubjectFromToken(token);
            if (username != null) {
                attributes.put("username", username);
                return true;
            }
        }

        return false;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
    }
}
