package newCar.socket_app.service.secure;

import newCar.socket_app.model.Team;
import org.springframework.stereotype.Service;

@Service
public class TestJwtValidator implements JwtValidator {
    @Override
    public Long getUserId(String token) {
        return 1234L;
    }

    @Override
    public Team getTeam(String token) {
        return Team.TRAVEL;
    }

    @Override
    public boolean validateToken(String token) {
        return true;
    }

    @Override
    public boolean validateAdminToken(String token) {
        return false;
    }
}
