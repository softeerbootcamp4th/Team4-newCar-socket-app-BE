package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newCar.socket_app.model.entity.NoticeMessageEntity;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class NoticeMessage extends Message implements Serializable {
    @JsonProperty("content")
    private String content;

    @JsonCreator
    public NoticeMessage(@JsonProperty("id") String id, @JsonProperty("content") String content) {
        super(id);
        this.content = content;
    }

    public static NoticeMessage from(NoticeMessageEntity entity){
        NoticeMessage noticeMessage = new NoticeMessage();

        noticeMessage.setId("1-" + entity.getTimeStamp());
        noticeMessage.setContent(entity.getMessage());

        return noticeMessage;
    }
}
