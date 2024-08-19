package newCar.socket_app.model.session;

import lombok.Getter;
import lombok.Setter;
import newCar.socket_app.model.Team;

@Getter @Setter
public class UserSession extends Session {
    private Team team;

    public UserSession(Long accountId, Team team) {
        super(accountId);  // 부모 클래스(Session)의 생성자를 호출
        this.team = team;
    }
}
