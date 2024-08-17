package newCar.socket_app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import newCar.socket_app.exception.ChatMessageNotFoundException;
import newCar.socket_app.exception.InvalidChatMessageException;
import newCar.socket_app.exception.InvalidSessionException;
import newCar.socket_app.exception.SessionNotFoundException;
import newCar.socket_app.model.ChatMessage;
import newCar.socket_app.model.ChatMessageReceived;
import newCar.socket_app.model.Team;
import newCar.socket_app.service.MessagePublisherService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessagePublisherService messagePublisherService;

    // client -> /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload ChatMessage chatMessageReceived   //test!!!!
    ) {
        //validateSession(sessionAttributes);
        //validateChatMessage(chatMessageReceived);

        //ChatMessage chatMessage = new ChatMessage(chatMessageReceived);
        //chatMessage.setId(sessionAttributes.get("id").toString());
        //chatMessage.setTeam(((Team)sessionAttributes.get("team")).getCode());
        if(chatMessageReceived.getSender() == null){
            chatMessageReceived.setSender("junha");
        }
        if(chatMessageReceived.getTeam() == null){
            chatMessageReceived.setTeam(Team.TRAVEL.getCode());
        }

        messagePublisherService.publish("/topic/chat", chatMessageReceived);
    }

    private void validateSession(Map<String, Object> sessionAttributes) {
        if(sessionAttributes == null || sessionAttributes.isEmpty()){
            throw new SessionNotFoundException("세션이 만료되었거나 존재하지 않습니다. 다시 로그인해 주세요.");
        }
        if(sessionAttributes.get("userId") == null || sessionAttributes.get("team") == null){
            throw new InvalidSessionException("비정상적인 접근입니다.");
        }
    }

    private void validateChatMessage(ChatMessageReceived chatMessage) {
        if(chatMessage == null){
            throw new ChatMessageNotFoundException("메시지 규격이 올바르지 않습니다.");
        }

        if(chatMessage.getContent().isEmpty() || chatMessage.getContent().length() > 50){
            throw new InvalidChatMessageException("메시지 본문은 공백 포함 50자 이하만 가능합니다.");
        }
    }

}