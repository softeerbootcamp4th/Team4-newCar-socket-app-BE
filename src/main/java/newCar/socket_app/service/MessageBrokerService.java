package newCar.socket_app.service;

import newCar.socket_app.model.ChatMessage;

public interface MessageBrokerService {
    public void sendMessage(String topic, ChatMessage message);
}
