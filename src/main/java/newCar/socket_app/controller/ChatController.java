package newCar.socket_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.exception.*;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.ChatMessageReceived;
import newCar.socket_app.model.chat.Message;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.service.message.BufferedMessageService;
import newCar.socket_app.service.message.MessagePublisherService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessagePublisherService messagePublisherService;
    private final BufferedMessageService bufferedMessageService;

    //client -> /app/chat.getHistory
    @MessageMapping("/chat.getHistory")
    @SendToUser("/queue/chatHistory")
    public ArrayList<Message> getHistory(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes
    ) {
        validateHistoryRequest(sessionAttributes);
        return bufferedMessageService.getChatHistory();
    }

    // client -> /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload ChatMessageReceived chatMessageReceived
    ) {
        validateUserSession(sessionAttributes);
        validateChatMessage(chatMessageReceived);

        UserSession session = (UserSession) sessionAttributes.get("session");

        messagePublisherService.publish("/topic/chat", ChatMessage.from(chatMessageReceived, session));
    }

    private void validateHistoryRequest(Map<String, Object> sessionAttributes) {
        Boolean hasAlreadyRequested = (Boolean) sessionAttributes.get("hasAlreadyRequestedHistory");

        if (Boolean.TRUE.equals(hasAlreadyRequested)) {
            // 이미 요청한 경우 빈 큐를 반환하거나 오류 메시지를 전송할 수 있습니다.
            throw new ChatHistoryAlreadyRequestedException("과거 채팅 내역 요청은 한 번만 가능합니다.");
        } else {
            // 아직 요청하지 않은 경우 플래그를 설정하고 채팅 히스토리를 반환
            sessionAttributes.put("hasAlreadyRequestedHistory", true);
        }
    }

    private void validateUserSession(Map<String, Object> sessionAttributes) {
        if(sessionAttributes == null || sessionAttributes.isEmpty()){
            throw new SessionNotFoundException("세션이 만료되었거나 존재하지 않습니다. 로그인해 주세요.");
        }

        Object session = sessionAttributes.get("session");
        if(!(session instanceof UserSession)){
            throw new InvalidSessionException("비정상적인 접근입니다.");
        }
    }

    private void validateChatMessage(ChatMessageReceived chatMessageReceived) {
        if(chatMessageReceived == null){
            throw new ChatMessageNotFoundException("메시지 규격이 올바르지 않습니다.");
        }

        if(chatMessageReceived.getContent().isEmpty() || chatMessageReceived.getContent().length() > 50){
            throw new InvalidChatMessageException("메시지 본문은 공백 포함 50자 이하만 가능합니다.");
        }
    }

}
