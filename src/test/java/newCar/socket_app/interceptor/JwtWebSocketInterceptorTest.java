package newCar.socket_app.interceptor;

import newCar.socket_app.model.Team;
import newCar.socket_app.model.session.AdminSession;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.service.secure.JwtValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtWebSocketInterceptorTest {

    private JwtWebSocketInterceptor jwtWebSocketInterceptor;

    @Mock
    private JwtValidator jwtValidator;

    @Mock
    private WebSocketHandler webSocketHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtWebSocketInterceptor = new JwtWebSocketInterceptor(jwtValidator);
    }

    @Test
    public void testBeforeHandshake_WithoutToken() throws Exception {
        // Arrange
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletServerHttpRequest servletRequest = new ServletServerHttpRequest(mockRequest);

        Map<String, Object> attributes = new HashMap<>();

        // Act
        //boolean result = jwtWebSocketInterceptor.beforeHandshake(servletRequest, new ServletServerHttpResponse(), webSocketHandler, attributes);

        // Assert
        //assertTrue(result); // Note: Interceptor doesn't reject the handshake even without a token.
    }

    @Test
    @DisplayName("유효한 Admin 토큰으로 AdminSession을 생성하고, AccountId는 1이어야 한다.")
    public void testBeforeHandshake_WithValidAdminToken() throws Exception {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setQueryString("Authorization=validAdminToken");
        ServletServerHttpRequest servletRequest = new ServletServerHttpRequest(mockRequest);

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        ServerHttpResponse servletResponse = new ServletServerHttpResponse(mockResponse);

        when(jwtValidator.validateToken("validAdminToken")).thenReturn(true);
        when(jwtValidator.validateAdminToken("validAdminToken")).thenReturn(true);

        Map<String, Object> attributes = new HashMap<>();

        //when
        boolean result = jwtWebSocketInterceptor.beforeHandshake(servletRequest, servletResponse, webSocketHandler, attributes);

        //then
        assertTrue(result);

        Object session = attributes.get("session");
        assertInstanceOf(AdminSession.class, session);

        AdminSession adminSession = (AdminSession) session;
        assertEquals(1L, adminSession.getAccountId());
    }


    @Test
    @DisplayName("유효한 User 토큰으로 UserSession을 생성하고, 해독된 토큰 정보가 올바르게 포함되어야 한다.")
    public void testBeforeHandshake_WithValidUserToken() throws Exception {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setQueryString("Authorization=validUserToken");
        ServletServerHttpRequest servletRequest = new ServletServerHttpRequest(mockRequest);

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        ServerHttpResponse servletResponse = new ServletServerHttpResponse(mockResponse);

        when(jwtValidator.validateToken("validUserToken")).thenReturn(true);
        when(jwtValidator.validateAdminToken("validUserToken")).thenReturn(false);
        when(jwtValidator.getUserId("validUserToken")).thenReturn(1L);
        when(jwtValidator.getTeam("validUserToken")).thenReturn(Team.TRAVEL);

        Map<String, Object> attributes = new HashMap<>();

        //when
        boolean result = jwtWebSocketInterceptor.beforeHandshake(servletRequest, servletResponse, webSocketHandler, attributes);

        //then
        assertTrue(result);

        Object session = attributes.get("session");
        assertInstanceOf(UserSession.class, session);

        UserSession userSession = (UserSession) session;
        assertEquals(1L, userSession.getAccountId());
        assertEquals(Team.TRAVEL, userSession.getTeam());
    }

    /*
    @Test
    public void testBeforeHandshake_WithInvalidToken() throws Exception {
        // Arrange
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setQueryString("Authorization=invalidToken");
        ServletServerHttpRequest servletRequest = new ServletServerHttpRequest(mockRequest);

        when(jwtValidator.validateToken("invalidToken")).thenReturn(false);

        Map<String, Object> attributes = new HashMap<>();

        // Act
        boolean result = jwtWebSocketInterceptor.beforeHandshake(servletRequest, new ServletServerHttpResponse(), webSocketHandler, attributes);

        // Assert
        assertTrue(result); // Note: Interceptor doesn't reject the handshake even with an invalid token.
    }

    */
}
