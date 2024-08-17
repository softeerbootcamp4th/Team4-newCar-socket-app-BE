package newCar.socket_app.service.message;

import newCar.socket_app.model.ChatMessage;

public interface MessagePublisherService {
    public void publish(String topic, ChatMessage message);
}
