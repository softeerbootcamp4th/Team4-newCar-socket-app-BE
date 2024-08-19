package newCar.socket_app.controller;

import newCar.socket_app.exception.InvalidSessionException;
import newCar.socket_app.exception.SessionNotFoundException;
import newCar.socket_app.model.chat.BlockMessage;
import newCar.socket_app.model.chat.NoticeMessage;
import newCar.socket_app.model.session.AdminSession;
import newCar.socket_app.service.message.MessagePublisherService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class AdminChatController {

    @Qualifier("simpMessagePublisherService")
    private final MessagePublisherService messagePublisherService;

    AdminChatController(MessagePublisherService messagePublisherService) {
        this.messagePublisherService = messagePublisherService;
    }

    @MessageMapping("/chat.sendBlock")
    public void blockMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload BlockMessage blockMessageReceived
    ) {
        validateAdminSession(sessionAttributes);
        blockMessageReceived.generateUniqueId(1L);
        messagePublisherService.publish("/topic/block", blockMessageReceived);
    }

    @MessageMapping("/chat.sendNotice")
    public void sendNotice(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload NoticeMessage noticeMessageReceived
    ) {
        validateAdminSession(sessionAttributes);
        noticeMessageReceived.generateUniqueId(1L);
        messagePublisherService.publish("/topic/notice", noticeMessageReceived);
    }

    private void validateAdminSession(Map<String, Object> sessionAttributes) {
        if(sessionAttributes == null || sessionAttributes.isEmpty()){
            throw new SessionNotFoundException("세션이 만료되었거나 존재하지 않습니다. 로그인해 주세요.");
        }

        Object session = sessionAttributes.get("session");
        if(!(session instanceof AdminSession)){
            throw new InvalidSessionException("비정상적인 접근입니다.");
        }
    }


}
