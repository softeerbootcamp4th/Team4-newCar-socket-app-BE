package newCar.socket_app.service.message;

import newCar.socket_app.model.chat.ChatMessage;

public interface MessagePublisherService {
    public void publish(String topic, ChatMessage message);
}
