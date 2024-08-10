package newCar.socket_app.exception;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {}
    public InvalidSessionException(String message) {
        super(message);
    }
}
