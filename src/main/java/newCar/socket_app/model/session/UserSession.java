package newCar.socket_app.model.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import newCar.socket_app.model.Team;

@Getter @Setter
@AllArgsConstructor
public class UserSession extends Session {
    private Long userId;
    private Team team;
}
