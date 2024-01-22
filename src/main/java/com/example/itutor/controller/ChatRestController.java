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

    // Get the count of all messages in the system
    @GetMapping("/messages/count")
    public ResponseEntity<Long> getTotalMessageCount() {
        long messageCount = chatMessageService.countTotalMessages();
        return ResponseEntity.ok(messageCount);
    }

    // Get the count of messages in a specific chat using the chatId
    // for the chatId always use the String senderUsername_recipientUsername
    // for example asdf_qwer is the chat between asdf and qwer
    @GetMapping("/messages/count/{chatId}")
    public ResponseEntity<Long> getMessageCountByChatId(@PathVariable String chatId) {
        long messageCount = chatMessageService.countMessagesByChatId(chatId);
        return ResponseEntity.ok(messageCount);
    }

    // man soll sich die nachrichten holen können mit userID also welcher user gerade eingeloggt ist soll seinen nachrichten verlauf sehen

    // ist ja REST also muss man Update delete, post undso also andere methoden auch machen

    // more endpoints
}
