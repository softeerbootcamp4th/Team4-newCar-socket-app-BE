package newCar.socket_app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import newCar.socket_app.model.Team;
import newCar.socket_app.model.chat.ChatMessage;

@Entity
@Getter @Setter
@Table(name = "chat")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Team team;

    private String message;

    private String timeStamp;

    public static ChatMessageEntity from(ChatMessage chatMessage){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setUserId(Long.parseLong(chatMessage.getSender()));
        chatMessageEntity.setTeam(Team.fromCode(chatMessage.getTeam()));
        chatMessageEntity.setMessage(chatMessage.getContent());
        chatMessageEntity.setTimeStamp(chatMessage.getTimeStamp());
        return chatMessageEntity;
    }
}
