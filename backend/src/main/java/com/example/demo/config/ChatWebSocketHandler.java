package com.example.demo.config;

import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Parse tin nhắn từ JSON
        String jsonMessage = (String) message.getPayload();
        JSONObject jsonObject = new JSONObject(jsonMessage);

        String recipientUsername = jsonObject.getString("recipientUsername");
        String messageUser = jsonObject.getString("message");
        String senderUsername = jsonObject.getString("from");

//        System.out.println("from : " + senderUsername);
//        System.out.println("to: " +  recipientUsername);
//        System.out.println("mess : " + messageUser);

        for (WebSocketSession s : sessions) {
            if (s != null && s.isOpen() && !s.equals(session)) {
                    JSONObject messageObject = new JSONObject();
                    messageObject.put("recipientUsername", recipientUsername);
                    messageObject.put("message", messageUser);
                    messageObject.put("from", senderUsername);

                    // Gửi tin nhắn đến người nhận
                    s.sendMessage(new TextMessage(messageObject.toString()));
//                }
            }
        }
    }

    // Phương thức này giúp lấy tên người dùng từ session
    private String getUsernameFromSession(WebSocketSession session) {
        // Lấy thông tin từ URL của session
        URI uri = session.getUri();
        String queryString = uri.getQuery();
        // Phân tích chuỗi truy vấn để lấy giá trị của tham số "username"
        String[] queryParams = queryString.split("&");
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("username")) {
                // Trả về giá trị của tham số "username"
                return keyValue[1];
            }
        }

        return null;
    }







    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

