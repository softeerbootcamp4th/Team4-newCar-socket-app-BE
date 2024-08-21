package newCar.socket_app.model.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BlockMessage extends Message implements Serializable {
    @JsonProperty("blockId")
    private String blockId;

    @JsonCreator
    public BlockMessage(@JsonProperty("id") String id, @JsonProperty("blockId") String blockId) {
        super(id);
        this.blockId = blockId;
    }
}


