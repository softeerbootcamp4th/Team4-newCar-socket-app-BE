package newCar.socket_app.service.message;

import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.Message;

import java.util.ArrayList;

public interface BufferedMessageService {

    public void addMessage(String channel, String message);
    public void fetchChatHistory();
    public ArrayList<Message> getChatHistory();
}
