package newCar.socket_app.service.secure;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import newCar.socket_app.config.JwtConfig;
import newCar.socket_app.exception.UnverifiedTokenException;
import newCar.socket_app.model.Team;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.security.SignatureException;

@Service @Primary
@RequiredArgsConstructor
public class JwtValidatorImpl implements JwtValidator {

    private final JwtConfig jwtConfig;

    @Override
    public Long getUserId(String token){
        try{
            return getClaims(token).get("userId", Long.class);
        } catch (Exception e){
            throw new UnverifiedTokenException("토큰 검증을 먼저 진행해야합니다.");
        }
    } //토큰에서 유저 Id를 추출

    @Override
    public Team getTeam(String token){
        try{
            return claimsToTeam(getClaims(token).get("team", String.class));
        } catch (Exception e){
            throw new UnverifiedTokenException("토큰 검증을 먼저 진행해야합니다.");
        }
    } //토큰에서 유저 Team을 추출

    @Override
    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    } //JWT 토큰 유효성 검증

    @Override
    public boolean validateAdminToken(String token){
        try{
            return getClaims(token).get("role", String.class).equals("admin");
        } catch (Exception e){
            return false;
        }
    }
    //JWT 토큰이 admin의 역할을 담고 있는지 검증

    private Team claimsToTeam(String team){
        return switch (team) {
            case "PET" -> Team.PET;
            case "TRAVEL" -> Team.TRAVEL;
            case "LEISURE" -> Team.LEISURE;
            case "SPACE" -> Team.SPACE;
            default -> Team.NONE;
        };
    }

    private Claims getClaims(String token)
            throws ExpiredJwtException, SignatureException, IllegalArgumentException, MalformedJwtException, UnsupportedJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] secretKey(){
        return Decoders.BASE64.decode(jwtConfig.getSecret());
    }//원래는 secret key 값을 바로 바꿔 줄 수 있었으나 그 메소드는 deprecated 되어서
    //디코딩을 해주어야 합니다

}