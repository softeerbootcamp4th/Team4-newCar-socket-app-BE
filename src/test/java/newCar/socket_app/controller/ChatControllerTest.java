package newCar.socket_app.controller;

import newCar.socket_app.exception.*;
import newCar.socket_app.model.Team;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.ChatMessageReceived;
import newCar.socket_app.model.chat.Message;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.service.message.BufferedMessageService;
import newCar.socket_app.service.message.MessagePublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    private ChatController chatController;

    @Mock
    private MessagePublisherService messagePublisherService;

    @Mock
    private BufferedMessageService bufferedMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatController = new ChatController(messagePublisherService, bufferedMessageService);
    }

    @Test
    @DisplayName("히스토리 내역을 조회한 후에는 조회 기록이 남아있어야한다.")
    void testGetHistory_withValidSession_shouldReturnChatHistory() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        ArrayList<Message> expectedHistory = new ArrayList<>();
        when(bufferedMessageService.getChatHistory()).thenReturn(expectedHistory);

        // Act
        ArrayList<Message> result = chatController.getHistory(sessionAttributes);

        // Assert
        assertEquals(expectedHistory, result);
        assertTrue((Boolean) sessionAttributes.get("hasAlreadyRequestedHistory"));
    }

    @Test
    @DisplayName("히스토리 내역 조회는 한 번만 가능하다.")
    void testGetHistory_withAlreadyRequestedHistory_shouldThrowException() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("hasAlreadyRequestedHistory", true);

        // Act & Assert
        assertThrows(ChatHistoryAlreadyRequestedException.class, () -> chatController.getHistory(sessionAttributes));
    }

    @Test
    @DisplayName("유저 세션이 존재하지 않으면 메시지 발행이 불가능하다.")
    void testSendMessage_withInvalidSession_shouldThrowException() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();

        // Act & Assert
        assertThrows(SessionNotFoundException.class, () -> chatController.sendMessage(sessionAttributes, new ChatMessageReceived()));
    }

    @Test
    @DisplayName("채팅 메시지는 본문이 존재해야한다.")
    void testSendMessage_withInvalidChatMessage_shouldThrowException() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("session", new UserSession(1L, Team.PET));

        ChatMessageReceived invalidMessage = new ChatMessageReceived();
        invalidMessage.setContent(""); // Invalid because it's empty

        // Act & Assert
        assertThrows(InvalidChatMessageException.class, () -> chatController.sendMessage(sessionAttributes, invalidMessage));
    }

    @Test
    @DisplayName("채팅 메시지의 최대 길이는 50자이다.")
    void testSendMessage_withLongChatMessage_shouldThrowException() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("session", new UserSession(1L, Team.PET));

        ChatMessageReceived longMessage = new ChatMessageReceived();
        longMessage.setContent("This is a very long message that exceeds the 50 character limit.");

        // Act & Assert
        assertThrows(InvalidChatMessageException.class, () -> chatController.sendMessage(sessionAttributes, longMessage));
    }
}
