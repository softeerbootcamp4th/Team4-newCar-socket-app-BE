package newCar.socket_app.model.entity;

import newCar.socket_app.model.Team;
import newCar.socket_app.model.chat.ChatMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageEntityTest {

    @Test
    @DisplayName("ChatMessage -> ChatMessageEntity 변환")
    public void testChatMessageToEntityConversion() {
        // Given
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId("123-2024-08-21T15:45:30.123");
        chatMessage.setSender("123");
        chatMessage.setTeam("P");
        chatMessage.setContent("test success!!");

        // When
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.from(chatMessage);

        // Then
        assertEquals(123L, chatMessageEntity.getUserId());
        assertEquals(Team.PET, chatMessageEntity.getTeam());
        assertEquals("test success!!", chatMessageEntity.getMessage());
        assertEquals("2024-08-21T15:45:30.123", chatMessageEntity.getTimeStamp());
    }

    @Test
    @DisplayName("ChatMessageEntity -> ChatMessage 변환")
    public void testEntityToChatMessageConversion() {
        // Given
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setId(1L);
        chatMessageEntity.setUserId(123L);
        chatMessageEntity.setTeam(Team.PET);
        chatMessageEntity.setMessage("test success!!");
        chatMessageEntity.setTimeStamp("2024-08-21T15:45:30.123");

        // When
        ChatMessage chatMessage = ChatMessage.from(chatMessageEntity);

        // Then
        assertEquals("123-2024-08-21T15:45:30.123", chatMessage.getId());
        assertEquals("123", chatMessage.getSender());
        assertEquals("P", chatMessage.getTeam());
        assertEquals("test success!!", chatMessage.getContent());
    }

}