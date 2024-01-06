package com.example.itutor.controller;

import com.example.itutor.domain.User;
import com.example.itutor.domain.chat.ChatMessage;
import com.example.itutor.domain.chat.ChatMessageService;
import com.example.itutor.domain.chat.ChatNotification;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final UserRepositoryI userRepositoryI;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification (
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/user/me")
    public ResponseEntity<Optional<User>> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userRepositoryI.findByUsername(username);
        return ResponseEntity.ok(user);
    }




    /*

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
    */

    @RequestMapping(value = "/chats/chat", method = RequestMethod.GET) //http://localhost:8080/chats/chat
    public String showChat(HttpServletRequest request, Model model) {

        return "chats/chat";
    }
}
