package newCar.socket_app.service.game;

import newCar.socket_app.model.game.GameData;

public interface GameStateService {
    public void updateGameState(GameData gameData);
    public void pushGameState();
}
