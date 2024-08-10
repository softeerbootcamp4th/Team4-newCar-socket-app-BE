package newCar.socket_app.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {}
    public SessionNotFoundException(String message) {
        super(message);
    }
}
