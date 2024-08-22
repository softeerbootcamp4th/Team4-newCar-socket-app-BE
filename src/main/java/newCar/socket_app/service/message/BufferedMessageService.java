package newCar.socket_app.service.message;

import newCar.socket_app.model.chat.Message;

import java.util.ArrayList;

public interface BufferedMessageService {

    public void addMessage(String channel, String message);
    public void fetchHistory();
    public ArrayList<Message> getChatHistory();
}
