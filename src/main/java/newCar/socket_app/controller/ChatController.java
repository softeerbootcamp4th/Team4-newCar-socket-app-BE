package newCar.socket_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.exception.ChatMessageNotFoundException;
import newCar.socket_app.exception.InvalidChatMessageException;
import newCar.socket_app.exception.InvalidSessionException;
import newCar.socket_app.exception.SessionNotFoundException;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.ChatMessageReceived;
import newCar.socket_app.model.session.Session;
import newCar.socket_app.model.session.UserSession;
import newCar.socket_app.service.message.MessagePublisherService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessagePublisherService messagePublisherService;

    // client -> /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload ChatMessageReceived chatMessageReceived
    ) {
        validateUserSession(sessionAttributes);
        validateChatMessage(chatMessageReceived);

        UserSession session = (UserSession) sessionAttributes.get("session");

        log.info("인가된 유저의 채팅 : {}", chatMessageReceived.getContent());
        log.info("인가된 유저 세션 : {}", session);
        messagePublisherService.publish("/topic/chat", ChatMessage.from(chatMessageReceived, session));
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
