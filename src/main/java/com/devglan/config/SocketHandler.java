package com.devglan.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class SocketHandler extends TextWebSocketHandler {

    private SessionStore sessionStore;

	public SocketHandler(SessionStore sessionStore) {
		this.sessionStore = sessionStore;
	}

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);
        String msg = value.get("message");
        String receiverUser = value.get("To");
        WebSocketSession wss = this.sessionStore.getSessionForUser(receiverUser);
        wss.sendMessage(new TextMessage( msg));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionStore.store(session);
    }

}