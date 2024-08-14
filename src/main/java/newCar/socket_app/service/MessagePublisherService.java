package newCar.socket_app.service;

import newCar.socket_app.model.ChatMessage;
import newCar.socket_app.model.ChatMessageReceived;

public interface MessagePublisherService {
    public void publish(String topic, ChatMessage message);
}
