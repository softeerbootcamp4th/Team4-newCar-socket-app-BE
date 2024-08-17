package newCar.socket_app.service.message;

public interface BufferedMessageService {
    public void addMessage(String message);
    public void flushBuffer();
}
