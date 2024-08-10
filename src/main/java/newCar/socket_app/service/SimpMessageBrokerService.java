package newCar.socket_app.service;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpMessageBrokerService implements MessageBrokerService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(String topic, ChatMessage message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
