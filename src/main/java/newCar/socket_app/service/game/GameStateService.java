package newCar.socket_app.service.game;

import newCar.socket_app.model.GameData;

public interface GameStateService {
    public void updateGameState(GameData gameData);
    public void pushGameState();
}
