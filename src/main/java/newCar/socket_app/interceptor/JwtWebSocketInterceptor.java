package newCar.socket_app.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.session.AdminSession;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.model.Team;
import newCar.socket_app.service.secure.JwtTokenProvider;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtWebSocketInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {
        log.info("웹소켓 연결요청 HTTP로 들어옴!!!!!!!!");
        request.getHeaders().forEach((h, v) -> {
            log.info("header = {}", h);
            log.info("value = {}", v);
        });
        log.info(request.toString());
        String token = request.getHeaders().getFirst("Authorization");

        if (token != null) {
            log.info("확인된 토큰 : {}", token);
        }

        // WebSocket 연결 시 클라이언트를 식별할 수 있는 고유한 세션을 생성한다. -> 웹소켓 연결 자체가 고유한 세션 식별자로 묶여있다.
        // 클라이언트가 소켓을 통해 보내는 메시지에 세션 식별자를 '명시적으로' 작성하지 않아도 서버는 클라이언트를 식별할 수 있다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            if(jwtTokenProvider.validateAdminToken(token)){
                attributes.put("session", new AdminSession());
                return true;
            }

            Long userId = jwtTokenProvider.getUserId(token);
            Team team = jwtTokenProvider.getTeam(token);
            attributes.put("session", new UserSession(userId, team));
            log.info("유저 연결 : {}", userId);
            log.info("팀 : {}", team);
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
