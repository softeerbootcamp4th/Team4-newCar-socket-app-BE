package newCar.socket_app.service.secure;

import newCar.socket_app.model.Team;


public interface JwtValidator {

    public Long getUserId(String token); //토큰에서 유저 Id를 추출

    public Team getTeam(String token); //토큰에서 유저 Team을 추출

    public boolean validateToken(String token); //JWT 토큰 유효성 검증

    public boolean validateAdminToken(String token);
}
