package com.example.itutor.service;

import com.example.itutor.domain.AiMessages;
import com.example.itutor.domain.User;

public interface OpenAIServiceI {
    AiMessages saveMessages(AiMessages aiMessages);
    AiMessages getMessagesById(Long id);
    void deleteMessages(AiMessages aiMessages);


    String getChatResponse(AiMessages aiMessages);
    AiMessages getMessagesForUser(User user);
    AiMessages resetMessagesForUser(User user);

    void deleteMessagesForUser(User user);
}
