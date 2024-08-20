package newCar.socket_app.service.message;

import newCar.socket_app.model.chat.ChatMessage;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public interface BufferedMessageService {

    public void addMessage(String message);
    public void flushBuffer();
    public ArrayList<ChatMessage> getChatMessages();
}
