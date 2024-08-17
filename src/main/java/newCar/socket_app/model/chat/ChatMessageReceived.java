package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChatMessageReceived implements Serializable {
    private String content;
}
