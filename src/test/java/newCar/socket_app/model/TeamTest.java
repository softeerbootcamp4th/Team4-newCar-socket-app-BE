package newCar.socket_app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void values() {
        for (Team team : Team.values()) {
            System.out.println(team.getCode());
        }
    }
}