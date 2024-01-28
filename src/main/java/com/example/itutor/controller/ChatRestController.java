package com.example.itutor.controller;

import com.example.itutor.domain.chat.ChatMessage;
import com.example.itutor.service.impl.ChatMessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/messages")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.save(chatMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<ChatMessage> updateMessage(@PathVariable long id, @RequestBody ChatMessage updatedMessage) {
        return chatMessageService.findById(id)
                .map(message -> {
                    message.setContent(updatedMessage.getContent());
                    return ResponseEntity.ok(chatMessageService.save(message));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable long id) {
        Optional<ChatMessage> message = chatMessageService.findById(id);
        if (message.isPresent()) {
            chatMessageService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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

    // get the chat between two users, using their username
    @GetMapping("/messages/{userName1}/{userName2}")
    public ResponseEntity<List<ChatMessage>> getChatBetweenUsers(
            @PathVariable String userName1,
            @PathVariable String userName2,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ChatMessage> messagesPage = chatMessageService.findChatMessages(userName1, userName2, page, size);
        return ResponseEntity.ok(messagesPage.getContent());
    }
}
