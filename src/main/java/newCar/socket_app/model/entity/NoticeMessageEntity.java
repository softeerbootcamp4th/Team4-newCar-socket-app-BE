package newCar.socket_app.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import newCar.socket_app.model.chat.NoticeMessage;

@Entity
@Getter @Setter
@Table(name = "notice")
public class NoticeMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String timeStamp;

    public static NoticeMessageEntity from(NoticeMessage noticeMessage){
        NoticeMessageEntity noticeMessageEntity = new NoticeMessageEntity();
        noticeMessageEntity.setMessage(noticeMessage.getContent());
        noticeMessageEntity.setTimeStamp(noticeMessage.getTimeStamp());
        return noticeMessageEntity;
    }
}
