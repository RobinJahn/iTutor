package com.example.itutor.domain.chat;

import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {

    @Id
    private Long id;
    private String senderId;
    private String recipientId;
    private String content;
}
