package com.example.itutor.repository;

import com.example.itutor.domain.chat.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    Page<ChatMessage> findByChatId(String chatId, Pageable pageable);
    Optional<ChatMessage> findById(long id);
    Page<ChatMessage> findAllByOrderByTimestampDesc(Pageable pageable);
    long countByChatId(String chatId);

    void deleteById(long id);
}
