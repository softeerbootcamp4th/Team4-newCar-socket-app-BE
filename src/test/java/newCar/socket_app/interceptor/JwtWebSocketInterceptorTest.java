package newCar.socket_app.interceptor;

import newCar.socket_app.model.Team;
import newCar.socket_app.service.secure.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtWebSocketInterceptorTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private ServerHttpResponse response;

    @Mock
    private WebSocketHandler wsHandler;

    private JwtWebSocketInterceptor jwtWebSocketInterceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtWebSocketInterceptor = new JwtWebSocketInterceptor(jwtTokenProvider);
    }

    @Test
    void testBeforeHandshakeWithValidToken() throws Exception {
        String token = "validToken";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        when(request.getHeaders()).thenReturn(headers);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getUserId(token)).thenReturn(1234L);
        when(jwtTokenProvider.getTeam(token)).thenReturn(Team.TRAVEL);

        Map<String, Object> attributes = new HashMap<>();

        boolean result = jwtWebSocketInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        assertTrue(result);
        verify(jwtTokenProvider, times(1)).validateToken(token);
        verify(jwtTokenProvider, times(1)).getUserId(token);
        verify(jwtTokenProvider, times(1)).getTeam(token);
        verify(response, never()).setStatusCode(HttpStatus.UNAUTHORIZED);

        assertTrue(attributes.containsKey("session"));
    }

    @Test
    void testBeforeHandshakeWithInvalidToken() throws Exception {
        String token = "invalidToken";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        when(request.getHeaders()).thenReturn(headers);
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        Map<String, Object> attributes = new HashMap<>();

        boolean result = jwtWebSocketInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        assertFalse(result);
        verify(jwtTokenProvider, times(1)).validateToken(token);
        verify(response, times(1)).setStatusCode(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void testBeforeHandshakeWithoutToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        when(request.getHeaders()).thenReturn(headers);

        Map<String, Object> attributes = new HashMap<>();

        boolean result = jwtWebSocketInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        assertFalse(result);
        verify(jwtTokenProvider, never()).validateToken(anyString());
        verify(response, times(1)).setStatusCode(HttpStatus.UNAUTHORIZED);
    }
}
