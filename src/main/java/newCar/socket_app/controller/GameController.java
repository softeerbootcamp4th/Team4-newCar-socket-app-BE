package newCar.socket_app.controller;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.GameData;
import newCar.socket_app.service.GameStateService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameStateService gameStateService;

    // client -> /app/game.sendGameData
    @MessageMapping("/game.sendGameData")
    public void sendGameData(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @Payload GameData gameData
    ) {
        gameStateService.updateGameState(gameData);
    }
}
