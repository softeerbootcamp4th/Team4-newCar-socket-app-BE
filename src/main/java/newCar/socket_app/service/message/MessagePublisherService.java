package newCar.socket_app.service.message;

import newCar.socket_app.model.chat.Message;

public interface MessagePublisherService {
    public void publish(String topic, Message message);
}
