package newCar.socket_app.exception;

public class ChatHistoryAlreadyRequestedException extends RuntimeException {
    public ChatHistoryAlreadyRequestedException() {}
    public ChatHistoryAlreadyRequestedException(String message) {
        super(message);
    }
}
