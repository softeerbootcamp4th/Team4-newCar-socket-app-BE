package newCar.socket_app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {
    PET("P"),
    TRAVEL("T"),
    SPACE("S"),
    LEISURE("L"),
    NONE("N");

    private final String code;
}

