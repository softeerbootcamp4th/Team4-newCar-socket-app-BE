package newCar.socket_app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ChatMessage implements Serializable {
    private String content;
    private String sender;
    private String team;
}
