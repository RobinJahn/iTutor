package com.example.itutor.domain.chat.chatroom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.lang.annotation.Documented;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    private long id;
    private String chatId;
    private String senderId;
    private String recipientId;

}
