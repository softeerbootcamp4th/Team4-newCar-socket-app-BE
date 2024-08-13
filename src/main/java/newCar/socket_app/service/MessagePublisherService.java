package newCar.socket_app.service;

import newCar.socket_app.model.ChatMessage;

public interface MessagePublisherService {
    public void publish(String topic, ChatMessage message);
}
