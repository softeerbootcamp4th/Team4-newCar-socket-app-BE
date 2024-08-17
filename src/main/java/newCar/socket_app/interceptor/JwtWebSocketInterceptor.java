package newCar.socket_app.interceptor;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.SocketSession;
import newCar.socket_app.model.Team;
import newCar.socket_app.service.secure.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

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
        String token = request.getHeaders().getFirst("Authorization");

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // WebSocket 연결 시 클라이언트를 식별할 수 있는 고유한 세션을 생성한다. -> 웹소켓 연결 자체가 고유한 세션 식별자로 묶여있다.
            // 클라이언트가 소켓을 통해 보내는 메시지에 세션 식별자를 '명시적으로' 작성하지 않아도 서버는 클라이언트를 식별할 수 있다.
            Long userId = jwtTokenProvider.getUserId(token);
            Team team = jwtTokenProvider.getTeam(token);

            // 세션에 저장될 속성 초기화. -> 서버 내부에 저장됨.
            attributes.put("session", new SocketSession(userId, team));
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
