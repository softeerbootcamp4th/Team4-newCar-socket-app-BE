package newCar.socket_app.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessageReceived implements Serializable {
    /***for test***/
    private String sender;
    private String team;
    /*****/

    private String content;
}
