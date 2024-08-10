package newCar.socket_app.exception;

public class ChatMessageNotFoundException extends RuntimeException {
    public ChatMessageNotFoundException() {}
    public ChatMessageNotFoundException(String message) {
        super(message);
    }
}
