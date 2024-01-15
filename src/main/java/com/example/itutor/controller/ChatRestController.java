package com.example.itutor.controller;

import com.example.itutor.domain.chat.ChatMessage;
import com.example.itutor.service.impl.ChatMessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatMessageService chatMessageService;

    public ChatRestController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Get all messages from all chats
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getAllMessages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ChatMessage> messagesPage = chatMessageService.findAllMessages(page, size);
        List<ChatMessage> messages = messagesPage.getContent();
        return ResponseEntity.ok(messages);
    }


    // more endpoints
}
