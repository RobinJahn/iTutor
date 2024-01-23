package com.example.itutor.repository;

import com.example.itutor.domain.AiMessages;
import com.example.itutor.domain.Content;
import com.example.itutor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiMessagesRepositoryI extends JpaRepository<AiMessages, Long> {
    Optional<AiMessages> findByUserUsername(String username);
    Optional<AiMessages> findByUser(User user);
}
