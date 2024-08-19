package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newCar.socket_app.model.session.UserSession;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessage extends Message implements Serializable {
    private String sender;
    private String team;
    private String content;

    public static ChatMessage from(ChatMessageReceived chatMessageReceived, UserSession userSession) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.generateUniqueId(userSession.getAccountId());
        chatMessage.setSender(userSession.getAccountId().toString());
        chatMessage.setTeam(userSession.getTeam().getCode());
        chatMessage.setContent(chatMessageReceived.getContent());

        return chatMessage;
    }
}
