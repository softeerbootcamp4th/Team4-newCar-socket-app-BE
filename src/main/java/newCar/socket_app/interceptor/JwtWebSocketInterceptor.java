package newCar.socket_app.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.session.AdminSession;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.model.Team;
import newCar.socket_app.service.secure.JwtValidator;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtWebSocketInterceptor implements HandshakeInterceptor {

    private final JwtValidator jwtValidator;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {

        // URI를 통해 쿼리 파라미터를 추출
        String token = null;
        ServletServerHttpRequest servletRequest;
        if (request instanceof ServletServerHttpRequest) {
            servletRequest = (ServletServerHttpRequest) request;
        } else {
            return false;
        }

        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(servletRequest.getURI()).build().getQueryParams();
        try{
            token = queryParams.get("Authorization").get(0);
        } catch (NullPointerException ne){
            return true;
        }

        // WebSocket 연결 시 클라이언트를 식별할 수 있는 고유한 세션을 생성한다. -> 웹소켓 연결 자체가 고유한 세션 식별자로 묶여있다.
        // 클라이언트가 소켓을 통해 보내는 메시지에 세션 식별자를 '명시적으로' 작성하지 않아도 서버는 클라이언트를 식별할 수 있다.
        if (token != null && jwtValidator.validateToken(token)) {
            if(jwtValidator.validateAdminToken(token)){
                attributes.put("session", new AdminSession(1L));
                return true;
            }

            Long userId = jwtValidator.getUserId(token);
            Team team = jwtValidator.getTeam(token);
            attributes.put("session", new UserSession(userId, team));
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
