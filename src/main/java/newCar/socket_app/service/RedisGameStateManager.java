package newCar.socket_app.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.GameData;
import newCar.socket_app.model.GameState;
import newCar.socket_app.model.Team;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisGameStateManager implements GameStateService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private final String redisGameStateKeyString = "GameState";

    @PostConstruct
    public void init() {
        Boolean isExist = redisTemplate.hasKey(redisGameStateKeyString);
        if (isExist != null && isExist) {
            return;
        }

        Map<String, Long> gameState = new HashMap<>();
        gameState.put(Team.PET.getCode(), 0L);
        gameState.put(Team.TRAVEL.getCode(), 0L);
        gameState.put(Team.SPACE.getCode(), 0L);
        gameState.put(Team.LEISURE.getCode(), 0L);

        redisTemplate.opsForHash().putAll(redisGameStateKeyString, gameState);
    }

    @Override
    public void updateGameState(GameData gameData) {
        Arrays.stream(Team.values())
                .filter(team -> gameData.getGameData(team) > 0L)
                .forEach(team -> redisTemplate.opsForHash().increment("GameState", team.getCode(), gameData.getGameData(team)));
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void pushGameState() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisGameStateKeyString);

        GameState gameState = new GameState();

        Arrays.stream(Team.values())
                .forEach(team -> {
                    Long value = (Long) entries.get(team.getCode());
                    if (value != null) {
                        gameState.setGameState(team, value);
                    }
                });

        messagingTemplate.convertAndSend("/topic/game", gameState);
    }
}
