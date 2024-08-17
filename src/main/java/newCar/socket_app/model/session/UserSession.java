package newCar.socket_app.model.session;

import lombok.AllArgsConstructor;
import newCar.socket_app.model.Team;

@AllArgsConstructor
public class UserSession extends Session {
    private Long userId;
    private Team team;
}
