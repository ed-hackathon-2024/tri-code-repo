package org.tricode.hackathonserverside.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    public Message(MessageType type, String text, Conversation conversation) {
        this.type = type;
        this.text = text;
        this.conversation = conversation;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public enum MessageType {
        USER,
        ASSISTANT
    }
}
