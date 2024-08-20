package newCar.socket_app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import newCar.socket_app.model.chat.ChatMessage;

@Entity
@Getter @Setter
@Table(name = "chat")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    public ChatMessageEntity from(ChatMessage chatMessage){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setUserId(Long.parseLong(chatMessage.getSender()));
        chatMessageEntity.setMessage(chatMessage.getContent());
        return chatMessageEntity;
    }
}
