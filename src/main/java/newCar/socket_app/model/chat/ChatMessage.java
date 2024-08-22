package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newCar.socket_app.model.entity.ChatMessageEntity;
import newCar.socket_app.model.session.UserSession;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessage extends Message implements Serializable {
    @JsonProperty("sender")
    private String sender;

    @JsonProperty("team")
    private String team;

    @JsonProperty("content")
    private String content;

    public static ChatMessage from(ChatMessageReceived chatMessageReceived, UserSession userSession) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.generateUniqueId(userSession.getAccountId());
        chatMessage.setSender(userSession.getAccountId().toString());
        chatMessage.setTeam(userSession.getTeam().getCode());
        chatMessage.setContent(chatMessageReceived.getContent());

        return chatMessage;
    }

    public static ChatMessage from(ChatMessageEntity chatMessageEntity){
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setId(chatMessageEntity.getUserId() + "-" + chatMessageEntity.getTimeStamp());
        chatMessage.setSender(chatMessageEntity.getUserId().toString());
        chatMessage.setTeam(chatMessageEntity.getTeam().getCode());
        chatMessage.setContent(chatMessageEntity.getMessage());

        return chatMessage;
    }

    @JsonCreator
    public ChatMessage(@JsonProperty("id") String id,
                       @JsonProperty("sender") String sender,
                       @JsonProperty("team") String team,
                       @JsonProperty("content") String content) {
        super(id);
        this.sender = sender;
        this.team = team;
        this.content = content;
    }
}
