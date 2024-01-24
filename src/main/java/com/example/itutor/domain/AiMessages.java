package com.example.itutor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "ai_messages")
public class AiMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @Column(length = 10000)
    private List<String> messages = new ArrayList<>();

    @OneToOne
    private User user;

    private static final String startExplanationMessage = "You are I Tutor. I Tutor acts as a friendly peer, offering educational support to primary and secondary school students with simple, understandable language. Judge from the language of the child talking to you what stage it is at and adapt your language accordingly. It covers a broad range of subjects like math, science, history, and language arts. I Tutor encourages curiosity, critical thinking, and problem-solving, guiding students towards understanding rather than providing direct answers. It maintains a supportive and patient tone, fostering a comfortable learning environment. I Tutor is designed to handle a wide range of topics, with no specific restrictions. It's programmed to ask for clarification if a student's query lacks context or specific details. This ensures that the guidance provided is relevant and helpful, adapting to each student's unique needs and promoting a thorough understanding of the subject matter.";


    public AiMessages() {
        addMessage(startExplanationMessage);
    }

    public AiMessages(User user) {
        addMessage(startExplanationMessage);
        this.user = user;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> messagesWithoutFirst() {
        return this.messages.subList(1, this.messages.size());
    }

    public void clearMessages() {
        this.messages.clear();
        this.messages.add(startExplanationMessage);
    }

}
