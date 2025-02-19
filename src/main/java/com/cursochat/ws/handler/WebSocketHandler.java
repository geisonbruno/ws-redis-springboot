package com.cursochat.ws.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(org.springframework.web.socket.WebSocketSession session) throws Exception {
        System.out.println("[afterConnectionEstablished] session id: " + session.getId());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    session.sendMessage(new TextMessage("Hello!" + UUID.randomUUID()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 2000L, 2000L);
    }

    @Override
    protected void handleTextMessage(org.springframework.web.socket.WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
        System.out.println("[handleTextMessage] message: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(org.springframework.web.socket.WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("[afterConnectionClosed] session id: " + session.getId());
    }

}
