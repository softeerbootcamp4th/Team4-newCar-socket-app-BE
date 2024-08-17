package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newCar.socket_app.model.session.Session;
import newCar.socket_app.model.session.UserSession;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessage implements Serializable {
    //private String id;
    private String sender;
    private String team;
    private String content;

    public static ChatMessage from(ChatMessageReceived chatMessageReceived, UserSession userSession) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setSender(userSession.getUserId().toString());
        chatMessage.setTeam(userSession.getTeam().getCode());
        chatMessage.setContent(chatMessageReceived.getContent());

        return chatMessage;
    }
}
