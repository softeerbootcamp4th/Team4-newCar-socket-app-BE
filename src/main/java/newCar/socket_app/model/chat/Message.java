package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class Message implements Serializable {
    @JsonProperty("id")
    private String id;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public void generateUniqueId(Long accountId){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.id = accountId.toString() + "-" + now.format(formatter);
    }

    public String getTimeStamp(){
        return id.substring(id.indexOf("-")+1);
    }
}
