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

    public static Team fromCode(String code) {
        for (Team team : Team.values()) {
            if (team.getCode().equals(code)) {
                return team;
            }
        }
        return Team.NONE;
    }
}

