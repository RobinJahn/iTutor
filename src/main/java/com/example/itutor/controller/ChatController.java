package com.example.itutor.controller;

import com.example.itutor.domain.chat.ChatMessage;
import com.example.itutor.domain.chat.MessageType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            Authentication authentication
    ) {
        String username = authentication.getName();
        headerAccessor.getSessionAttributes().put("username", username);
        chatMessage.setSender(username);
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }

    @RequestMapping(value = "/chats/chat", method = RequestMethod.GET) //http://localhost:8080/chats/chat
    public String showChat(HttpServletRequest request, Model model) {

        return "chats/chat";
    }
}
