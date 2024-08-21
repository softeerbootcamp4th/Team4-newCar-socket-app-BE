package newCar.socket_app.model.game;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newCar.socket_app.model.Team;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class GameState implements Serializable {
    private Long P; //PET
    private Long T; //TRAVEL
    private Long S; //SPACE
    private Long L; //LEISURE

    public Long getGameState(Team team){
        return switch (team){
            case PET -> P;
            case TRAVEL -> T;
            case SPACE -> S;
            case LEISURE -> L;
            case NONE -> P;
        };
    }

    public void setGameState(Team team, Long value){
        switch (team){
            case PET -> P = value;
            case TRAVEL -> T = value;
            case SPACE -> S = value;
            case LEISURE -> L = value;
        }
    }
}
