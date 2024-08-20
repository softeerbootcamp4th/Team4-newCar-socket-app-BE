package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class Message implements Serializable {
    private String id;

    public void generateUniqueId(Long accountId){
        this.id = accountId.toString() + "-" + System.currentTimeMillis();
    }
}
