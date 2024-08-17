package newCar.socket_app.model.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    NOTICE("N"),
    BLOCK("B"),
    MESSAGE("M");

    private final String code;
}
