package newCar.socket_app.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;

@Component
public class SocketExceptionHandler {

    @MessageExceptionHandler(RuntimeException.class)
    @SendToUser("/queue/errors")
    public String handleRuntimeException(RuntimeException e) {
        return e.getMessage();
    }
}
