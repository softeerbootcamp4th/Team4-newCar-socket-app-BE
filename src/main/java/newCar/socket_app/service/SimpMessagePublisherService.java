package newCar.socket_app.service;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.ChatMessage;
import newCar.socket_app.model.ChatMessageReceived;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpMessagePublisherService implements MessagePublisherService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void publish(String topic, ChatMessage message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
