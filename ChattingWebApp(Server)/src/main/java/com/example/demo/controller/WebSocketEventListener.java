package com.example.demo.controller;

import com.example.demo.model.MessageChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent sessionConnectedEvent){
        System.out.println("------------> new connection");
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent  sessionDisconnectEvent){
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
        if (username != null){
            logger.info("User Disconnected : " + username);
            MessageChat messageChat = new MessageChat();
            messageChat.setType(MessageChat.MessageType.LEAVE);
            messageChat.setSender(username);
            simpMessageSendingOperations.convertAndSend("/topic/public",messageChat);
        }
    }
}