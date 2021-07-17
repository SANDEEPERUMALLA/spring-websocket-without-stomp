package com.devglan.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class SessionStore {

    private Map<String, WebSocketSession> userToSessionMap = new HashMap<>();

    public WebSocketSession getSessionForUser(String user) {
        return userToSessionMap.get(user);
    }
    public void store(WebSocketSession session) {
        userToSessionMap.putIfAbsent((String) session.getAttributes().get("user"), session);
    }
}
