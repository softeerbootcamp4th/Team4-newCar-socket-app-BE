package newCar.socket_app.exception;

public class InvalidChatMessageException extends RuntimeException {
    public InvalidChatMessageException() {}
    public InvalidChatMessageException(String message) {
        super(message);
    }
}
