package newCar.socket_app.service;

import newCar.socket_app.model.GameData;
import newCar.socket_app.model.GameState;

public interface GameStateService {
    public void updateGameState(GameData gameData);
    public void pushGameState();
}
