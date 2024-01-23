package com.example.itutor.service.impl;

import com.example.itutor.domain.chat.ChatMessage;
import com.example.itutor.service.impl.ChatRoomService;
import com.example.itutor.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage){
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public Page<ChatMessage> findChatMessages(String senderId, String recipientId, int page, int size) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return chatId.map(id -> repository.findByChatId(id, pageable))
                        .orElse(Page.empty());
    }

    public Page<ChatMessage> findAllMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return repository.findAllByOrderByTimestampDesc(pageable);
    }

    public long countTotalMessages() {
        return repository.count();
    }

    public long countMessagesByChatId(String chatId) {
        return repository.countByChatId(chatId);
    }

    public Optional<ChatMessage> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
