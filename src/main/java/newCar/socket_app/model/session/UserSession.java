package newCar.socket_app.model.session;

import lombok.AllArgsConstructor;
import newCar.socket_app.model.Team;

@AllArgsConstructor
public class UserSession {
    private Long userId;
    private Team team;
}
